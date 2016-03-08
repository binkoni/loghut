package company.gonapps.loghut.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.filter.Filters;
import company.gonapps.loghut.filter.TitleFilter;
import company.gonapps.loghut.service.PostService;
import company.gonapps.loghut.service.PostService.SearchResult;

@Controller
public class SearchController extends BaseController {

	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
    @RequestMapping(value = "/search.do", method = RequestMethod.GET)
    public String search(ModelMap modelMap, HttpServletRequest request, 
    		int page_unit, int page) throws Exception {


    	Filters<PostDto> filters = new Filters<>();
    	String title = request.getParameter("title");
    	if(! title.equals("")) {
    		filters.append(new TitleFilter(title));
    	}
    	SearchResult searchResult = postService.search(page_unit, page, filters);
    	List<PostDto> posts = searchResult.getPosts();
    	
    	modelMap.put("posts", posts);
    	
    	if(page > 1) {
    		modelMap.put("previousPageLink", getSettingDao().getSetting("admin.url") + "/search.do?" 
    	            + request.getQueryString().replaceFirst("page=\\d+", "page=" + (page - 1)));
    	}
    	
    	modelMap.put("currentPage", page);
    	if(!searchResult.isLastPage()) modelMap.put("nextPageLink",
    			getSettingDao().getSetting("admin.url")
    			+ "/search.do?"
    	    	+ request.getQueryString().replaceFirst("page=\\d+", "page=" + (page + 1)));
    	
    	return "search";
    }
}
