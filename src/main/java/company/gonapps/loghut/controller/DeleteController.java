package company.gonapps.loghut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.service.PostService;

@Controller
public class DeleteController extends BaseController {
	
	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
    @RequestMapping(value = "/delete.do", method = RequestMethod.GET)
    public String delete(ModelMap modelMap, 
    		int year, 
    		int month, 
    		int day, 
    		int number, 
    		boolean secret_enabled) throws Exception {
    	PostDto post = new PostDto()
    	.setYear(year)
    	.setMonth(month)
    	.setDay(day)
    	.setNumber(number)
    	.setSecretEnabled(secret_enabled);
    	
    	modelMap.put("post", post);
    	
    	postService.delete(post);
    	
    	return "delete";
    }
}
