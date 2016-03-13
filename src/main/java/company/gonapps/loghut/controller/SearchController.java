package company.gonapps.loghut.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.filter.DaysFilter;
import company.gonapps.loghut.filter.Filters;
import company.gonapps.loghut.filter.MonthsFilter;
import company.gonapps.loghut.filter.TagsFilter;
import company.gonapps.loghut.filter.TextFilter;
import company.gonapps.loghut.filter.TitleFilter;
import company.gonapps.loghut.filter.YearsFilter;
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
    	if(title != null && ! title.equals("")) {
    		filters.append(new TitleFilter(title));
    	}
    	
    	String tag_namesString = request.getParameter("tag_names");
    	if(tag_namesString != null && ! tag_namesString.equals("")) {
    		List<String> tagNames = new LinkedList<>();
        	for(String tagName : tag_namesString.split("\\s*,\\s*")) {
        		if(tagName.matches("\\s*")) continue;
        		tagNames.add(tagName);
        	}
        	filters.append(new TagsFilter(tagNames));
    	}
    	
    	String text = request.getParameter("text");
    	if(text != null && ! text.equals("")) {
    		filters.append(new TextFilter(text));
    	}
    	
    	String yearsString = request.getParameter("years");
    	if(yearsString != null && ! yearsString.equals("")) {
    		List<String> years = new LinkedList<>();
        	for(String year : yearsString.split("\\s*,\\s*")) {
        		if(year.matches("\\s*")) continue;
        		years.add(year);
        	}
        	filters.append(new YearsFilter(years));
    	}
    	
    	String monthsString = request.getParameter("months");
    	if(monthsString != null && ! monthsString.equals("")) {
    		List<String> months = new LinkedList<>();
        	for(String month : monthsString.split("\\s*,\\s*")) {
        		if(month.matches("\\s*")) continue;
        		months.add(month);
        	}
        	filters.append(new MonthsFilter(months));
    	}
    	
    	String daysString = request.getParameter("days");
    	if(daysString != null && ! daysString.equals("")) {
    		List<String> days = new LinkedList<>();
        	for(String day : daysString.split("\\s*,\\s*")) {
        		if(day.matches("\\s*")) continue;
        		days.add(day);
        	}
        	filters.append(new DaysFilter(days));
    	}
    	
    	SearchResult searchResult = postService.search(page_unit, page, filters);
    	modelMap.put("posts", searchResult.getPosts());
    	
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
