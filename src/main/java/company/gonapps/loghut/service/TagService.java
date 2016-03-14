package company.gonapps.loghut.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.gonapps.loghut.dao.TagDao;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.exception.InvalidTagNameException;

import freemarker.template.TemplateException;

@Service
public class TagService {
	
	private TagDao tagDao;
	
	@Autowired
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	
	public void create(TagDto tag)
			throws IOException,
			TemplateException,
			InvalidTagNameException {
		
		tagDao.create(tag);
		
		String tagName = tag.getName();
		int year = tag.getPost().getYear();
		int month = tag.getPost().getMonth();
		
    	tagDao.createTagNameIndex();
    	tagDao.createYearIndex(tagName);
    	tagDao.createMonthIndex(tagName, year);
    	tagDao.createTagIndex(tagName, year, month);
	}
	
	public void delete(TagDto tag)
			throws IOException, 
			InvalidTagNameException,
			TemplateException {
		
		tagDao.delete(tag);
		
		String tagName = tag.getName();
		int year = tag.getPost().getYear();
		int month = tag.getPost().getMonth();
		
    	if(tagDao.monthExists(tagName, year, month)) tagDao.createTagIndex(tagName, year, month);
		if(tagDao.yearExists(tagName, year)) tagDao.createMonthIndex(tagName, year);
		if(tagDao.tagNameExists(tagName)) tagDao.createYearIndex(tagName);
		
		tagDao.createTagNameIndex();
		
	}
	
	public void refreshAll()
			throws IOException,
			TemplateException,
			InvalidTagNameException {
		for(String tagName : tagDao.getTagNames()) {
            for(String yearString : tagDao.getYears(tagName)) {
            	int year = new Integer(yearString);
            	for(String monthString : tagDao.getMonths(tagName, year)) {
            		int month = new Integer(monthString);
            		tagDao.createTagIndex(tagName, year, month);
            	}
            	tagDao.createMonthIndex(tagName, year);
            }
            tagDao.createYearIndex(tagName);
		}
		tagDao.createTagNameIndex();
	}
}
