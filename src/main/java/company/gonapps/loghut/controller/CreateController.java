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
public class CreateController extends BaseController {
	
	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(ModelMap modelMap, String title, boolean secret_enabled, String text, String tag_names) throws Exception {
    	PostDto post = new PostDto();
    	List<TagDto> tags = new LinkedList<>();

    	post.setTitle(title)
    	.setSecretEnabled(secret_enabled)
    	.setText(text);
    	
    	for(String tagName : tag_names.split("\\s*,\\s*")) {
    		if(tagName.matches("\\s*")) continue;
    		tags.add(new TagDto().setName(tagName));
    	}
    	
    	post.setTags(tags);  
    	
        postService.create(post);
        
        modelMap.put("post", post);
    	
        return "create";
    }
}
