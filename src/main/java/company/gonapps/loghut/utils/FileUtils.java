/**
* LogHut is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* LogHut is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with LogHut.  If not, see <http://www.gnu.org/licenses/>.
**/

package company.gonapps.loghut.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.output.CloseShieldOutputStream;

public class FileUtils {

	private static void addFileToArchive(TarArchiveOutputStream tarArchiveOutputStream,
			String path,
			String base) throws IOException {
        File file = new File(path);
        String entryName = base + file.getName();
        tarArchiveOutputStream.putArchiveEntry(new TarArchiveEntry(file, entryName));

        if(file.isFile()) {
    	   	try(FileInputStream fileInputStream = new FileInputStream(file)) {
                IOUtils.copy(fileInputStream, tarArchiveOutputStream);
    	   	}
    	   	tarArchiveOutputStream.closeArchiveEntry();
    	} else {
    		tarArchiveOutputStream.closeArchiveEntry();
    	    File[] children = file.listFiles();
    	    if(children != null) {
    	        for (File child : children)
    	            addFileToArchive(tarArchiveOutputStream, child.getAbsolutePath(), entryName + "/");
    	    }
    	}
    }
	
	public static void compress(String filePathString, OutputStream outputStream)
			throws IOException {
    	try(BufferedInputStream bufferedInputStream
    			= new BufferedInputStream(new FileInputStream(filePathString));
    			GzipCompressorOutputStream gzipCompressorOutputStream
    			= new GzipCompressorOutputStream(
    					new CloseShieldOutputStream(outputStream))) {
    	    IOUtils.copy(bufferedInputStream, gzipCompressorOutputStream);
    	}
    }
    
    public static void archive(List<String> filePathStrings, OutputStream outputStream)
    	    throws IOException {
    	try(TarArchiveOutputStream tarArchiveOutputStream
    		= new TarArchiveOutputStream(
    				new CloseShieldOutputStream(outputStream))) {
    		
        	for(String filePathString : filePathStrings)
                addFileToArchive(tarArchiveOutputStream, filePathString, "");
        	
        	tarArchiveOutputStream.finish();
        }
    }
    
	public static List<Path> scan(Path path) throws IOException {
    	return new FileScanner().scan(path);
    }
    
    public static List<String> scan(String pathString) throws IOException {
    	return new FileScanner().scan(pathString);
    }
	
    public static void rmdir(Path directoryPath, DirectoryStream.Filter<Path> ignoringFilter)
    		throws NotDirectoryException, IOException {
    	if(! directoryPath.toFile().isDirectory())
    		throw new NotDirectoryException(directoryPath.toString());
    	
    	try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) { 	
    		List<Path> ignoredPaths = new LinkedList<>();
    	
    	    for(Path path : directoryStream) {
    		    if(! ignoringFilter.accept(path)) return;
    		    ignoredPaths.add(path);
        	}
    	
        	for(Path ignoredPath : ignoredPaths) {
        	    Files.delete(ignoredPath);
        	}
    	
            Files.delete(directoryPath);
        }
    }
}

class FileScanner extends SimpleFileVisitor<Path> {
    List<Path> paths;
    
    public List<Path> scan(Path path) throws IOException {
    	paths = new LinkedList<>();
        Files.walkFileTree(path, this);
        return paths;
    }
    
    public List<String> scan(String pathString) throws IOException {
    	paths = new LinkedList<>();
        Files.walkFileTree(Paths.get(pathString), this);
        List<String> pathStrings = new LinkedList<>();
        for(Path path : paths) {
        	pathStrings.add(path.toString());
        }
        return pathStrings;
    }
    
	@Override
    public FileVisitResult visitFile(Path path, 
    		BasicFileAttributes attributes) throws IOException {
		paths.add(path);
    	return FileVisitResult.CONTINUE;
    }
}