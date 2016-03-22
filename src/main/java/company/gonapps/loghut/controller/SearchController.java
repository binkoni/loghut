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
    	
    	String searchFilter = request.getParameter("search_filter");    	
    	if(searchFilter == null) searchFilter = "";
    	modelMap.put("searchFilter", searchFilter);

    	String searchKeyword = request.getParameter("search_keyword");
    	if(searchKeyword == null) searchKeyword = "";
    	modelMap.put("searchKeyword", searchKeyword);;
    	
    	if(! searchKeyword.equals("")) {
    	    if(searchFilter.equals("title")) {
        		filters.append(new TitleFilter(searchKeyword));
    	    } else if(searchFilter.equals("tag_names")) {
        		List<String> tagNames = new LinkedList<>();
        	    for(String tagName : searchKeyword.split("\\s*,\\s*")) {
            		if(tagName.matches("\\s*")) continue;
        		    tagNames.add(tagName);
        	    }
        	    filters.append(new TagsFilter(tagNames));
    	    } else if(searchFilter.equals("text")) {
        		filters.append(new TextFilter(searchKeyword));
    	    } else if(searchFilter.equals("years")) {
        		List<String> years = new LinkedList<>();
        	    for(String year : searchKeyword.split("\\s*,\\s*")) {
            		if(year.matches("\\s*")) continue;
        		    years.add(year);
        	    }
        	    filters.append(new YearsFilter(years));
    	    } else if(searchFilter.equals("months")) {
        		List<String> months = new LinkedList<>();
        	    for(String month : searchKeyword.split("\\s*,\\s*")) {
        		    if(month.matches("\\s*")) continue;
        		    months.add(month);
        	    }
        	    filters.append(new MonthsFilter(months));
    	    } else if(searchFilter.equals("days")) {
        		List<String> days = new LinkedList<>();
        	    for(String day : searchKeyword.split("\\s*,\\s*")) {
            		if(day.matches("\\s*")) continue;
        		    days.add(day);
        	    }
        	    filters.append(new DaysFilter(days));
    	    }
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