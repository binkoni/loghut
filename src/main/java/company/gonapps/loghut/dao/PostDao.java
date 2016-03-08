package company.gonapps.loghut.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
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

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import company.gonapps.loghut.comparator.MonthComparator;
import company.gonapps.loghut.comparator.PostDtoComparator;
import company.gonapps.loghut.comparator.YearComparator;
import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.exception.InvalidTagNameException;
import company.gonapps.loghut.utils.FileUtils;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Repository
public class PostDao {
	
	private SettingDao settingDao;
	
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private Template postTemplate;
	private Template mainIndexTemplate;
	private Template yearIndexTemplate;
	private Template monthIndexTemplate;
	private Template postIndexTemplate;
	
	private static Pattern postPathStringPattern = 
			Pattern.compile(
					"(?<year>\\d\\d\\d\\d)/(?<month>\\d\\d)/(?<day>\\d\\d)_(?<number>\\d+)\\.html(?<secret>s?)$");
	
	private String getPostPathString(PostDto post) {
		return settingDao.getSetting("post.directory") + post.getLocalPath();
	}
	
	private String getYearIndexPathString() {
		return settingDao.getSetting("post.directory")
		+ "/index.html";
	}
	
	private String getMonthIndexPathString(int year) {
		return settingDao.getSetting("post.directory")
		+ "/"
		+ String.format("%04d", year)
		+ "/index.html";
	}
	
	private String getPostIndexPathString(int year, int month) {
		return settingDao.getSetting("post.directory")
		+ "/" 
		+ String.format("%04d", year)
		+ "/" 
		+ String.format("%02d", month)
		+ "/index.html";
	}
	
	private String getMainIndexPathString() {
		return settingDao.getSetting("blog.directory") + "/index.html";
	}
		
	@Autowired
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}
	
	@Autowired
	public void setFreemarkerConfiguerer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
	public void create(PostDto post) throws IOException, TemplateException {
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("post", post);
		if(postTemplate == null)
			postTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("post.ftl");
		postTemplate.process(modelMap, temporaryBuffer);
		
		Path postPath = Paths.get(getPostPathString(post));
		Files.createDirectories(postPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(postPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void compress(PostDto post) throws IOException {
		try(BufferedOutputStream bufferedOutputStream
				= new BufferedOutputStream(
						new FileOutputStream(getPostPathString(post) + ".gz"))) {
			FileUtils.compress(getPostPathString(post), bufferedOutputStream);
		}
	}
	
	public PostDto getPostObject(int year, int month, int day, int number, boolean secretEnabled) throws IOException {
		return new PostDto().setYear(year)
		.setMonth(month)
		.setDay(day)
		.setNumber(number)
		.setSecretEnabled(secretEnabled);
	}
	
	public List<PostDto> getPostObjects() throws IOException {		
		List<PostDto> postObjects = new LinkedList<>();
		
        for(String postPathString : FileUtils.scan(settingDao.getSetting("post.directory"))) {
			Matcher matcher = postPathStringPattern.matcher(postPathString);
			if(! matcher.find()) continue;
			postObjects.add(getPostObject(new Integer(matcher.group("year")),
					new Integer(matcher.group("month")),
					new Integer(matcher.group("day")),
					new Integer(matcher.group("number")),
					matcher.group("secret").equals("s")));
		}
        
        Collections.sort(postObjects, new PostDtoComparator());
        
        return postObjects;
	}
	
	public PostDto get(PostDto postObject) throws IOException, InvalidTagNameException {
		PostDto post = getPostObject(postObject.getYear(),
				postObject.getMonth(),
				postObject.getDay(),
				postObject.getNumber(), 
				postObject.getSecretEnabled());
		
		Document document
		= Jsoup.parse(new File(getPostPathString(post)), "UTF-8");
		
		post.setTitle(document.select("#post_title").first().text());
	    post.setText(document.select("#post_text").first().html());
	    List<TagDto> tags = new LinkedList<>();
	    
	    for(Element tagElement : document.select("#post_tags").first().children()) {
	    	if(tagElement.text().matches("\\s*")) continue;
			tags.add(new TagDto().setName(tagElement.text()));
	    }
	    
	    post.setTags(tags);
        return post;
	}

	public PostDto get(int year,
			int month,
			int day,
			int number,
			boolean secretEnabled) throws IOException, InvalidTagNameException {
		return get(getPostObject(year, month, day, number, secretEnabled));
	}

	public List<PostDto> getList(int year, int month) throws IOException, InvalidTagNameException {
		List<PostDto> posts = new LinkedList<>();
		
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("post.directory") 
						+ "/"
						+ String.format("%04d", year)
						+ "/"
						+ String.format("%02d", month)))) {
			
			for(Path path : directoryStream) {
				Matcher matcher = postPathStringPattern.matcher(path.toString()); 
				if(matcher.find())
					posts.add(get(Integer.parseInt(matcher.group("year")),
							Integer.parseInt(matcher.group("month")),
							Integer.parseInt(matcher.group("day")),
							Integer.parseInt(matcher.group("number")),
							(matcher.group("secret").equals("s"))));
			}
		}
		Collections.sort(posts, new PostDtoComparator());

		return posts;
	}
	
	public List<PostDto> getList(List<PostDto> postObjects) throws IOException, InvalidTagNameException {
		List<PostDto> posts = new LinkedList<>();
		for(PostDto postObject : postObjects) {
			posts.add(get(postObject));
		}
		return posts;
	}
	
	public void backup(OutputStream outputStream) throws IOException {
		List<String> filePaths = new LinkedList<>();
		
		filePaths.add(settingDao.getSetting("blog.directory") + "/index.html");
		filePaths.add(settingDao.getSetting("blog.directory") + "/index.html.gz");
		filePaths.add(settingDao.getSetting("post.directory"));
		filePaths.add(settingDao.getSetting("tag.directory"));
        try(GzipCompressorOutputStream gzipCompressorOutputStream
        		= new GzipCompressorOutputStream(new BufferedOutputStream(outputStream))) {
        	FileUtils.archive(filePaths, gzipCompressorOutputStream);
        }
	}
	
	public void delete(PostDto post) throws IOException {
		
		Path postPath = Paths.get(getPostPathString(post));
		
		Files.delete(postPath);
		
		FileUtils.rmdir(postPath.getParent(), new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return ! postPathStringPattern.matcher(path.toString()).find();
			}	
    	}); 
		
    	FileUtils.rmdir(postPath.getParent().getParent(),
    			new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path path) throws IOException {
				return (! Pattern.compile("/\\d\\d$").matcher(path.toString()).find())
						|| (! Files.isDirectory(path));
			}
    	});
	}
	
	public void deleteCompression(PostDto post) throws IOException {
		Files.delete(Paths.get(getPostPathString(post) + ".gz"));
	}
	
	public boolean yearExists(int year) {
		return Files.exists(Paths.get(settingDao.getSetting("post.directory") 
				+ "/"
				+ String.format("%04d", year)));		
	}
	
	public boolean monthExists(int year, int month) {
		return Files.exists(Paths.get(settingDao.getSetting("post.directory") 
				+ "/"
				+ String.format("%04d", year)
				+ "/"
				+ String.format("%02d", month)));
	}
	
	public List<String> getYears() throws IOException {
		List<String> years = new LinkedList<>();
		Pattern pattern = Pattern.compile("/(\\d\\d\\d\\d)$");
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(Paths.get(settingDao.getSetting("post.directory")))) {
			for(Path path : directoryStream) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					years.add(matcher.group(1));
			}
		}
		Collections.sort(years, new YearComparator());
		return years;
	}
	
	public List<String> getMonths(int year) throws IOException {
		List<String> months = new LinkedList<>();
		Pattern pattern = Pattern.compile("/(\\d\\d)$");
		try(DirectoryStream<Path> directoryStream
				= Files.newDirectoryStream(
						Paths.get(settingDao.getSetting("post.directory") 
						+ "/"
						+ String.format("%04d", year)))) {
			
			for(Path path : directoryStream) {
				Matcher matcher = pattern.matcher(path.toString()); 
				if(matcher.find() && Files.isDirectory(path))
					months.add(matcher.group(1));
			}
		}
		Collections.sort(months, new MonthComparator());
		return months;
	}
	
	public void createYearIndex()
			throws TemplateNotFoundException,
			IOException,
			MalformedTemplateNameException,
			ParseException,
	        TemplateException {
		List<String> years = getYears();
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("years", years);
		if(yearIndexTemplate == null) yearIndexTemplate
		= freeMarkerConfigurer.getConfiguration().getTemplate("year_index.ftl");
		yearIndexTemplate.process(modelMap, temporaryBuffer);
		
        Path yearIndexPath = Paths.get(getYearIndexPathString());
		Files.createDirectories(yearIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(yearIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void compressYearIndex() throws IOException {
		String yearIndexPathString = getYearIndexPathString();
		try(BufferedOutputStream bufferedOutputStream
				= new BufferedOutputStream(
						new FileOutputStream(yearIndexPathString + ".gz"))) {
			FileUtils.compress(yearIndexPathString, bufferedOutputStream);
		}
	}
		
	public void createMonthIndex(int year)
			throws IOException,
			TemplateException {
		List<String> months = getMonths(year);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("months", months);
		if(monthIndexTemplate == null)
			monthIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("month_index.ftl");
		monthIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path monthIndexPath = Paths.get(getMonthIndexPathString(year));
		Files.createDirectories(monthIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(monthIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void compressMonthIndex(int year) throws IOException {
		String monthIndexPathString = getMonthIndexPathString(year);
		try(BufferedOutputStream bufferedOutputStream
				= new BufferedOutputStream(
						new FileOutputStream(monthIndexPathString + ".gz"))) {
			FileUtils.compress(monthIndexPathString, bufferedOutputStream);
		}
	}
	
	public void createPostIndex(int year, int month)
			throws IOException, 
			TemplateException,
			InvalidTagNameException {
		List<PostDto> posts = getList(year, month);
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("posts", posts);
		if(postIndexTemplate == null)
			postIndexTemplate
			= freeMarkerConfigurer.getConfiguration().getTemplate("post_index.ftl");
		postIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path postIndexPath = Paths.get(getPostIndexPathString(year, month));
		Files.createDirectories(postIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter
        		= new BufferedWriter(new FileWriter(postIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void compressPostIndex(int year, int month) throws IOException {
		String postIndexPathString = getPostIndexPathString(year, month);
		try(BufferedOutputStream bufferedOutputStream
				= new BufferedOutputStream(
						new FileOutputStream(postIndexPathString + ".gz"))) {
		    FileUtils.compress(postIndexPathString, bufferedOutputStream);
		}
	}
	
	public void createMainIndex(PostDto post) throws IOException, TemplateException {
		StringWriter temporaryBuffer = new StringWriter();
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("settings", settingDao);
		modelMap.put("post", post);
		if(mainIndexTemplate == null) mainIndexTemplate 
		= freeMarkerConfigurer.getConfiguration().getTemplate("main_index.ftl");
		mainIndexTemplate.process(modelMap, temporaryBuffer);
		
		Path mainIndexPath = Paths.get(getMainIndexPathString());
		Files.createDirectories(mainIndexPath.getParent());
		
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mainIndexPath.toFile()))) {
            bufferedWriter.write(temporaryBuffer.toString());
        }
	}
	
	public void compressMainIndex() throws IOException {
		String mainIndexPathString = getMainIndexPathString();
		try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				new FileOutputStream(mainIndexPathString + ".gz"))) {
			FileUtils.compress(mainIndexPathString, bufferedOutputStream);
		}
	}
}