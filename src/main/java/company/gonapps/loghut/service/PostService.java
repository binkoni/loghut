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

package company.gonapps.loghut.service;

import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.gonapps.loghut.dao.PostDao;
import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.exception.InvalidPageException;
import company.gonapps.loghut.filter.Filter;

@Service
public class PostService {
	
	private PostDao postDao;
	private TagService tagService;
	
	private List<PostDto> postObjectCache;
	private Map<String, PostDto> postCache = new HashMap<>();
	
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
	
	private PostDto getLatestPostObject() throws Exception {
		rrwl.readLock().lock();
		if(postObjectCache == null) {
			rrwl.readLock().unlock();
		    rrwl.writeLock().lock();
			postObjectCache = postDao.getPostObjects();
			rrwl.writeLock().unlock();
		} else rrwl.readLock().unlock();
		rrwl.readLock().lock();
		PostDto postObject = postObjectCache.get(0);
		rrwl.readLock().unlock();
		return postObject;
	}
	
	private PostDto getLatestPublicPostObject() throws Exception {
		rrwl.readLock().lock();
		if(postObjectCache == null) {
			rrwl.readLock().unlock();
			rrwl.writeLock().lock();
			postObjectCache = postDao.getPostObjects();
			rrwl.writeLock().unlock();
		} else rrwl.readLock().unlock();
		int i = 0;
		rrwl.readLock().lock();
		for(; postObjectCache.get(i).getSecretEnabled(); i++) {}
		PostDto postObject = postObjectCache.get(i);
		rrwl.readLock().unlock();
		return postObject;
	}
	
	private PostDto getLatestPublicPost() throws Exception {
		PostDto latestPublicPostObject = getLatestPublicPostObject();
		rrwl.readLock().lock();
		PostDto latestPublicPost = postCache.get(latestPublicPostObject.getLocalPath());
		if(latestPublicPost == null)
			latestPublicPost = postDao.get(latestPublicPostObject);
		rrwl.readLock().unlock();
		return latestPublicPost;
	}

	
	private void makeLatest(PostDto post) throws Exception {
		PostDto latestPostObject = getLatestPostObject();
		Calendar today = Calendar.getInstance();
		post.setYear(today.get(Calendar.YEAR));
		post.setMonth(today.get(Calendar.MONTH) + 1);
		post.setDay(today.get(Calendar.DAY_OF_MONTH));
		post.setNumber(1);
		
		if(post.getYear() == latestPostObject.getYear() 
				&& post.getMonth() == latestPostObject.getMonth()
				&& post.getDay() == latestPostObject.getDay())
			post.setNumber(latestPostObject.getNumber() + 1);
	}
	
	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
	
	@Autowired
	public void setTagService(TagService tagService) {
	    this.tagService = tagService;
	}
	
	public void create(PostDto post) throws Exception {
		
		makeLatest(post);
		postDao.create(post);
		postDao.compress(post);
		
		int year = post.getYear();
		int month = post.getMonth();
		
		PostDto latestPublicPost = post.getSecretEnabled() ? getLatestPublicPost() : post;
		postDao.createMainIndex(latestPublicPost);
		postDao.compressMainIndex();
		
		postDao.createYearIndex();
		postDao.compressYearIndex();
		
		postDao.createMonthIndex(year);
		postDao.compressMonthIndex(year);
        
		postDao.createPostIndex(year, month);
        postDao.compressPostIndex(year, month);
		
		for(TagDto tag : post.getTags())
        	tagService.create(tag);
		
		rrwl.writeLock().lock();
		postObjectCache.add(0, post);
		postCache.put(post.getLocalPath(), post);
		rrwl.writeLock().unlock();
	}
	
	public PostDto get(int year, int month, int day, int number, boolean secretEnabled)
			throws Exception {
		
		PostDto postObject = postDao.getPostObject(year, month, day, number, secretEnabled);
		rrwl.readLock().lock();
		PostDto post = postCache.get(postObject.getLocalPath());
		rrwl.readLock().unlock();
		
		if(post == null) {
			post = postDao.get(postObject);
			rrwl.writeLock().lock();
			postCache.put(post.getLocalPath(), post);
			rrwl.writeLock().unlock();
		}
		
		return post;
	}

	public SearchResult search(int pageUnit, int page, Filter<PostDto> filter)
			throws Exception {
        rrwl.readLock().lock();
		if(postObjectCache == null) {
			rrwl.readLock().unlock();
			rrwl.writeLock().lock();
			postObjectCache = postDao.getPostObjects();
			rrwl.writeLock().unlock();
		} else rrwl.readLock().unlock();
		
		List<PostDto> posts = new LinkedList<>();
		
    	for(PostDto postObject : postObjectCache) {
    		rrwl.readLock().lock();
    		PostDto post = postCache.get(postObject.getLocalPath());
    		rrwl.readLock().unlock();
    		if(post == null) {
    			post = postDao.get(postObject);
    			rrwl.writeLock().lock();
    			postCache.put(post.getLocalPath(), post);
    			rrwl.writeLock().unlock();
    		}
			
			if(filter == null || (filter != null && filter.test(post))) {
				posts.add(post);
				if(posts.size() >= page * pageUnit) break;
			}
		}
    	
    	int fromIndex = (page - 1) * pageUnit;
		int toIndex = page * pageUnit > posts.size() ?
				posts.size() : page * pageUnit;
		
		if(page <= 0
				|| pageUnit <= 0
				|| fromIndex < 0
				|| toIndex < 0
				|| fromIndex > toIndex) throw new InvalidPageException(); 
		
		return new SearchResult(posts.subList(fromIndex, toIndex), (fromIndex + pageUnit) > toIndex);
	}
	
	public void backup(OutputStream outputStream) throws Exception {
		postDao.backup(outputStream);
	}
	
    public void modify(PostDto oldPostObject, PostDto newPost) throws Exception {
    	rrwl.readLock().lock();
    	PostDto oldPost = postCache.get(oldPostObject.getLocalPath());
    	rrwl.readLock().unlock();
    	if(oldPost == null) oldPost = postDao.get(oldPostObject);
    	
    	postDao.delete(oldPostObject);
    	postDao.deleteCompression(oldPostObject);
    	
    	rrwl.writeLock().lock();
    	postObjectCache = null;
    	postCache.remove(oldPost.getLocalPath());
    	rrwl.writeLock().unlock();
    	
    	for(TagDto tag : oldPost.getTags())
	        tagService.delete(tag);
    	
    	postDao.create(newPost);
    	postDao.compress(newPost);
    	
    	postDao.createPostIndex(newPost.getYear(), newPost.getMonth());
        postDao.compressPostIndex(newPost.getYear(), newPost.getMonth());
        
		postDao.createMainIndex(newPost.getSecretEnabled() ? getLatestPublicPost() : newPost);
		postDao.compressMainIndex();

		
		for(TagDto tag : newPost.getTags())
        	tagService.create(tag);
	}
    
    public void refreshAll() throws Exception {
    	rrwl.readLock().lock();
    	if(postObjectCache == null) {
    		rrwl.readLock().unlock();
    		rrwl.writeLock().lock();
    		postObjectCache = postDao.getPostObjects();
    		rrwl.writeLock().unlock();
    	} else rrwl.readLock().unlock();
    	List<PostDto> posts = postDao.getList(postObjectCache);
    	
    	for(PostDto post : posts) {
    		postDao.create(post);
    		postDao.compress(post);
    	}
    	    	
		postDao.createMainIndex(getLatestPublicPost());
		postDao.compressMainIndex();
		
		postDao.createYearIndex();
		postDao.compressYearIndex();
		
		for(String year : postDao.getYears()) {
		    postDao.createMonthIndex(new Integer(year));
		    postDao.compressMonthIndex(new Integer(year));
		    for(String month : postDao.getMonths(new Integer(year))) {
                postDao.createPostIndex(new Integer(year), new Integer(month));
                postDao.compressPostIndex(new Integer(year), new Integer(month));
		    }
		}
		
		tagService.refreshAll();
    }
    
    public void delete(PostDto postObject) throws Exception {
    	rrwl.readLock().lock();
    	PostDto post = postCache.get(postObject.getLocalPath());
    	rrwl.readLock().unlock();
    	if(post == null) post = postDao.get(postObject);
    	
    	rrwl.writeLock().lock();
    	postObjectCache = null;
    	postCache.remove(post.getLocalPath());
    	rrwl.writeLock().unlock();
    	
    	for(TagDto tag : post.getTags())
	        tagService.delete(tag);
    	
    	postDao.delete(post);
    	try {
    	    postDao.deleteCompression(post);
    	} catch(NoSuchFileException exception) {}
    	
    	if(postDao.monthExists(post.getYear(), post.getMonth())) {
    		postDao.createPostIndex(post.getYear(), post.getMonth());
    		postDao.compressPostIndex(post.getYear(), post.getMonth());
    	}
    	
    	if(postDao.yearExists(post.getYear())) {
    		postDao.createMonthIndex(post.getYear());
    		postDao.compressMonthIndex(post.getYear());
    	}
    	
    	postDao.createYearIndex();
    	postDao.compressYearIndex();
    	postDao.createMainIndex(getLatestPublicPost());
    	postDao.compressMainIndex();
    }
    
    public static class SearchResult {
    	private List<PostDto> posts;
    	private boolean isLastPage;
    	public SearchResult(List<PostDto> posts, boolean isLastPage) {
    		this.posts = posts;
    		this.isLastPage = isLastPage;
    	}
    	
    	public List<PostDto> getPosts() {
    		return posts;
    	}
    	
    	public boolean isLastPage() {
    		return isLastPage;
    	}
    }
}
