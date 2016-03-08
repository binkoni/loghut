package company.gonapps.loghut.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.service.PostService;

@Controller
public class ModifyController extends BaseController {
	
	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
    @RequestMapping(value = "/modify.do", method = RequestMethod.POST)
    public String modify(ModelMap modelMap,
    		int year,
    		int month,
    		int day,
    		int number,
    		boolean old_secret_enabled,
    		String title,
    		boolean new_secret_enabled,
    		String text,
    		String tag_names) throws Exception {
    	
    	PostDto oldPost = new PostDto()
    	.setYear(year)
    	.setMonth(month)
    	.setDay(day)
    	.setNumber(number)
    	.setSecretEnabled(old_secret_enabled);
    	
    	PostDto newPost = new PostDto().setYear(year)
    	    	.setMonth(month)
    	    	.setDay(day)
    	    	.setNumber(number)
    	    	.setTitle(title)
    	    	.setSecretEnabled(new_secret_enabled)
    	    	.setText(text);
    	
    	List<TagDto> tags = new LinkedList<>();
    	
    	for(String tagName : tag_names.split("\\s*,\\s*")) {
    		if(tagName.matches("\\s*")) continue;
    		tags.add(new TagDto().setName(tagName));
    	}
    	
    	newPost.setTags(tags);  
    	
        postService.modify(oldPost, newPost);
        
        modelMap.put("post", newPost);
    	
        return "modify";
    }
}
