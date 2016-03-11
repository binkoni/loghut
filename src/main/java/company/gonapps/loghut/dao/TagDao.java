package company.gonapps.loghut.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import company.gonapps.loghut.comparator.MonthComparator;
import company.gonapps.loghut.comparator.TagDtoComparator;
import company.gonapps.loghut.comparator.YearComparator;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.exception.InvalidTagNameException;
import company.gonapps.loghut.utils.FileUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Repository
public class TagDao {
	private SettingDao settingDao;
	private PostDao postDao;
	private static final Pattern tagPathStringPattern = 
			Pattern.compile(
					"(?<name>.+)/(?<year>\\d\\d\\d\\d)/(?<month>\\d\\d)/(?<day>\\d\\d)_(?<number>\\d+)\\.html(?<secret>s?)$");
	private static final Pattern tagYearPattern = Pattern.compile("/(\\d\\d\\d\\d)$");
	private static final Pattern tagMonthPattern = Pattern.compile("/(\\d\\d)$");
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private Template tagNameIndexTemplate;
	private Template yearIndexTemplate;
	private Template monthIndexTemplate;
	private Template tagIndexTemplate;
	
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
		
	@Autowired
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}
	
	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
	
	@Autowired
	public void setFreemarkerConfiguerer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
    public boolean tagNameExists(String tagName) {
    	boolean exists;
    	rrwl.readLock().lock();
    	try {
    		exists = Files.exists(Paths.get(settingDao.getSetting("tag.directory")
    				+ "/"
    				+ tagName));
    	} finally {
    		rrwl.readLock().unlock();
    	}
    	return exists;
    }
    
    public boolean yearExists(String tagName, int year) {
    	boolean exists;
    	rrwl.readLock().lock();
    	try {
    		exists = Files.exists(Paths.get(settingDao.getSetting("tag.directory")
			        + "/"
			        + tagName
			        + "/"
			        + String.format("%04d", year)));
    	} finally {
    		rrwl.readLock().unlock();
    	}
    	return exists;
    }
    
    public boolean monthExists(String tagName, int year, int month) {
    	boolean exists;
    	rrwl.readLock().lock();
    	try {
    	    exists = Files.exists(Paths.get(settingDao.getSetting("tag.directory")
			        + "/"
			        + tagName
				    + "/"
				    + String.format("%04d", year)
				    + "/"
				    + String.format("%02d", month)));
    	} finally {
    		rrwl.readLock().unlock();
    	}
    	return exists;
    }
	
	public List<String> getTagNames() throws IOException {
    	List<String> names = new LinkedList<>();
		Pattern pattern = Pattern.compile("/([^/]+)$");
		rrwl.readLock().lock();
		try(DirectoryStream<Path> ds
				= Files.newDirectoryStream(Paths.get(settingDao.getSetting("tag.directory")))) {
			for(Path path : ds) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					names.add(matcher.group(1));
			}
		} finally {
			rrwl.readLock().unlock();
		}
		return names;
    }
    
    public List<String> getYears(String tagName) throws IOException {
		List<String> years = new LinkedList<>();
		rrwl.readLock().lock();
		try(DirectoryStream<Path> ds
				= Files.newDirectoryStream(Paths.get(settingDao.getSetting("tag.directory")
						+ "/"
						+ tagName))) {
			for(Path path : ds) {
				Matcher matcher = tagYearPattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					years.add(matcher.group(1));
			}
		} finally {
			rrwl.readLock().unlock();
		}
		Collections.sort(years, new YearComparator());
		return years;

    }
    
    public List<String> getMonths(String tagName, int year) throws IOException {
		List<String> months = new LinkedList<>();
		rrwl.readLock().lock();
		try(DirectoryStream<Path> ds
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("tag.directory")
								+ "/"
								+ tagName
								+ "/"
								+ String.format("%04d", year)))) {
			
			for(Path path : ds) {
				Matcher matcher = tagMonthPattern.matcher(path.toString()); 
				if(matcher.find() && path.toFile().isDirectory())
					months.add(matcher.group(1));
			}
		}
		rrwl.readLock().unlock();
		Collections.sort(months, new MonthComparator());
		return months;
    }
	
	public void createTagNameIndex()
			throws IOException,
			TemplateException {
		
		if(tagNameIndexTemplate == null)
			tagNameIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("tag_name_index.ftl");
		
		List<String> tagNames = getTagNames();
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("tagNames", tagNames);

		tagNameIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path tagNameIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/index.html");
		rrwl.writeLock().lock();
		Files.createDirectories(tagNameIndexPath.getParent());
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(tagNameIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        } finally {
        	rrwl.writeLock().unlock();
        }
	}

	public void createYearIndex(String tagName)
			throws IOException,
			TemplateException {
		
		if(yearIndexTemplate == null)
			yearIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("year_index.ftl");
		
		List<String> years = getYears(tagName);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("years", years);

		yearIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path yearIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/"
				+ tagName
				+ "/index.html");
		
		rrwl.writeLock().lock();
		Files.createDirectories(yearIndexPath.getParent());
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(yearIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        } finally {
        	rrwl.writeLock().unlock();
        }
	}
	
	public void createMonthIndex(String tagName, int year)
			throws IOException,
			TemplateException {
		
		if(monthIndexTemplate == null)
			monthIndexTemplate =
			freeMarkerConfigurer.getConfiguration().getTemplate("month_index.ftl");
		
		List<String> months = getMonths(tagName, year);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("months", months);

		monthIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path monthIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+"/"
				+ tagName
				+ "/"
				+ String.format("%04d", year)
				+ "/index.html");
		
		rrwl.writeLock().lock();
		Files.createDirectories(monthIndexPath.getParent());
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(monthIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        } finally {
        	rrwl.writeLock().unlock();
        }
	}
	
	public void createTagIndex(String tagName, int year, int month)
			throws IOException,
			InvalidTagNameException,
			TemplateException {
		
		if(tagIndexTemplate == null)
			tagIndexTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("tag_index.ftl");
		
		List<TagDto> tags = getList(tagName, year, month);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("tags", tags);

		tagIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path postIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/"
				+ tagName
				+ "/" 
				+ String.format("%04d", year)
				+ "/"
				+ String.format("%02d", month)
				+ "/index.html");
		rrwl.writeLock().lock();
		Files.createDirectories(postIndexPath.getParent());
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(postIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        } finally {
        	rrwl.writeLock().unlock();
        }
	}
	
    public void create(TagDto tag) throws IOException {
    	Path tagPath = Paths.get(settingDao.getSetting("tag.directory") + tag.getLocalPath());
    	rrwl.writeLock().lock();
    	Files.createDirectories(tagPath.getParent());
    	Files.createFile(tagPath);
    	rrwl.writeLock().unlock();
    }
    
    public List<TagDto> getList(String tagName, int year, int month)
    		throws IOException,
    		InvalidTagNameException {
		List<TagDto> tags = new LinkedList<>();
		rrwl.readLock().lock();
		try(DirectoryStream<Path> ds
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("tag.directory")
					    + "/"
					    + tagName
						+ "/"
						+ String.format("%04d", year)
						+ "/"
						+ String.format("%02d", month)))) {
			
			for(Path path : ds) {
				Matcher matcher = tagPathStringPattern.matcher(path.toString()); 
				if(matcher.find())
					tags.add(new TagDto().setName(matcher.group("name"))
							.setPost(postDao.get(Integer.parseInt(matcher.group("year")),
									Integer.parseInt(matcher.group("month")),
									Integer.parseInt(matcher.group("day")),
									Integer.parseInt(matcher.group("number")),
									matcher.group("secret").equals("s"))));
			}
		}
		rrwl.readLock().unlock();
		Collections.sort(tags, new TagDtoComparator());
		return tags;
    }
    
    public void delete(TagDto tag) throws IOException {
    	Path tagPath = Paths.get(settingDao.getSetting("tag.directory") + tag.getLocalPath());
    	
    	rrwl.writeLock().lock();
    	Files.delete(tagPath);
    	
    	FileUtils.rmdir(tagPath.getParent(), new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return ! tagPathStringPattern.matcher(path.toString()).find();
			}	
    	});
    	
    	FileUtils.rmdir(tagPath.getParent().getParent(), new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return (! tagMonthPattern.matcher(path.toString()).find())
						|| (! Files.isDirectory(path));
			}
    	});
    	
    	FileUtils.rmdir(tagPath.getParent().getParent().getParent(), new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return (! tagYearPattern.matcher(path.toString()).find())
						|| (! Files.isDirectory(path));
			}	
    	});
    	rrwl.writeLock().unlock();
    }
}