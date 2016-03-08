package company.gonapps.loghut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.gonapps.loghut.dao.TagDao;
import company.gonapps.loghut.dto.TagDto;

@Service
public class TagService {
	
	private TagDao tagDao;
	
	@Autowired
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	
	public void create(TagDto tag) throws Exception {
		
		tagDao.create(tag);
		
		String tagName = tag.getName();
		int year = tag.getPost().getYear();
		int month = tag.getPost().getMonth();
		
    	tagDao.createTagNameIndex();
    	tagDao.createYearIndex(tagName);
    	tagDao.createMonthIndex(tagName, year);
    	tagDao.createTagIndex(tagName, year, month);
	}
	
	public void delete(TagDto tag) throws Exception {
		
		tagDao.delete(tag);
		
		String tagName = tag.getName();
		int year = tag.getPost().getYear();
		int month = tag.getPost().getMonth();
		
    	if(tagDao.monthExists(tagName, year, month)) tagDao.createTagIndex(tagName, year, month);
		if(tagDao.yearExists(tagName, year)) tagDao.createMonthIndex(tagName, year);
		if(tagDao.tagNameExists(tagName)) tagDao.createYearIndex(tagName);
		
		tagDao.createTagNameIndex();
		
	}
}
