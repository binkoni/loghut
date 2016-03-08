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
	private static Pattern tagPathStringPattern = 
			Pattern.compile(
					"(?<name>.+)/(?<year>\\d\\d\\d\\d)/(?<month>\\d\\d)/(?<day>\\d\\d)_(?<number>\\d+)\\.html(?<secret>s?)$");
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private Template tagNameIndexTemplate;
	private Template yearIndexTemplate;
	private Template monthIndexTemplate;
	private Template tagIndexTemplate;
		
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
    	return Files.exists(Paths.get(settingDao.getSetting("tag.directory")
			    + "/"
			    + tagName));
    }
    
    public boolean yearExists(String tagName, int year) {
    	return Files.exists(Paths.get(settingDao.getSetting("tag.directory")
			    + "/"
			    + tagName
				+ "/"
				+ String.format("%04d", year)));
    }
    
    public boolean monthExists(String tagName, int year, int month) {
    	return Files.exists(Paths.get(settingDao.getSetting("tag.directory")
			    + "/"
			    + tagName
				+ "/"
				+ String.format("%04d", year)
				+ "/"
				+ String.format("%02d", month)));
    }
	
	public List<String> getTagNames() throws IOException {
    	List<String> names = new LinkedList<>();
		Pattern pattern = Pattern.compile("/([^/]+)$");
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(Paths.get(settingDao.getSetting("tag.directory")))) {
			for(Path path : directoryStream) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					names.add(matcher.group(1));
			}
		}
		return names;
    }
    
    public List<String> getYears(String tagName) throws IOException {
		List<String> years = new LinkedList<>();
		Pattern pattern = Pattern.compile("/(\\d\\d\\d\\d)$");
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(Paths.get(settingDao.getSetting("tag.directory")
						+ "/"
						+ tagName))) {
			for(Path path : directoryStream) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					years.add(matcher.group(1));
			}
		}
		Collections.sort(years, new YearComparator());
		return years;

    }
    
    public List<String> getMonths(String tagName, int year) throws IOException {
		List<String> months = new LinkedList<>();
		Pattern pattern = Pattern.compile("/(\\d\\d)$");
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("tag.directory")
								+ "/"
								+ tagName
								+ "/"
								+ String.format("%04d", year)))) {
			
			for(Path path : directoryStream) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && path.toFile().isDirectory())
					months.add(matcher.group(1));
			}
		}
		Collections.sort(months, new MonthComparator());
		return months;
    }
	
	public void createTagNameIndex()
			throws IOException,
			TemplateException {
		List<String> tagNames = getTagNames();
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("tagNames", tagNames);
		if(tagNameIndexTemplate == null)
			tagNameIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("tag_name_index.ftl");
		tagNameIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path tagNameIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/index.html");
		Files.createDirectories(tagNameIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(tagNameIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}

	public void createYearIndex(String tagName)
			throws IOException,
			TemplateException {
		List<String> years = getYears(tagName);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("years", years);
		if(yearIndexTemplate == null)
			yearIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("year_index.ftl");
		yearIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path yearIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/"
				+ tagName
				+ "/index.html");
		Files.createDirectories(yearIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(yearIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void createMonthIndex(String tagName, int year)
			throws IOException,
			TemplateException {
		List<String> months = getMonths(tagName, year);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("months", months);
		if(monthIndexTemplate == null)
			monthIndexTemplate =
			freeMarkerConfigurer.getConfiguration().getTemplate("month_index.ftl");
		monthIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path monthIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+"/"
				+ tagName
				+ "/"
				+ String.format("%04d", year)
				+ "/index.html");
		Files.createDirectories(monthIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(monthIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void createTagIndex(String tagName, int year, int month)
			throws IOException,
			InvalidTagNameException,
			TemplateException {
		List<TagDto> tags = getList(tagName, year, month);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("tags", tags);
		if(tagIndexTemplate == null)
			tagIndexTemplate = freeMarkerConfigurer.getConfiguration().getTemplate("tag_index.ftl");
		tagIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path postIndexPath = Paths.get(settingDao.getSetting("tag.directory")
				+ "/"
				+ tagName
				+ "/" 
				+ String.format("%04d", year)
				+ "/"
				+ String.format("%02d", month)
				+ "/index.html");
		Files.createDirectories(postIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(postIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
    public void create(TagDto tag) throws IOException {
    	Path tagPath = Paths.get(settingDao.getSetting("tag.directory") + tag.getLocalPath());
    	
    	Files.createDirectories(tagPath.getParent());
    	Files.createFile(tagPath);
    }
    
    public List<TagDto> getList(String tagName, int year, int month)
    		throws IOException,
    		InvalidTagNameException {
		List<TagDto> tags = new LinkedList<>();
		
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("tag.directory")
					    + "/"
					    + tagName
						+ "/"
						+ String.format("%04d", year)
						+ "/"
						+ String.format("%02d", month)))) {
			
			for(Path path : directoryStream) {
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
		Collections.sort(tags, new TagDtoComparator());
		return tags;
    }
    
    public void delete(TagDto tag) throws IOException {
    	Path tagPath = Paths.get(settingDao.getSetting("tag.directory") + tag.getLocalPath());
    	
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
				return (! Pattern.compile("/\\d\\d$").matcher(path.toString()).find())
						|| (! Files.isDirectory(path));
			}
    	});
    	
    	FileUtils.rmdir(tagPath.getParent().getParent().getParent(), new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return (! Pattern.compile("/\\d\\d\\d\\d$").matcher(path.toString()).find())
						|| (! Files.isDirectory(path));
			}	
    	});
    }
}