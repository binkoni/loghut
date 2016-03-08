package company.gonapps.loghut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.service.PostService;

@Controller
public class ModificationFormController extends BaseController {
	private PostService postService;
	
	@Autowired
	public void setPostDao(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(value = "/modification_form.do", method = RequestMethod.GET)
    public String modificationForm(ModelMap modelMap,
    		int year,
    		int month,
    		int day,
    		int number,
    		boolean secret_enabled) throws Exception {
		
		modelMap.put("post", postService.get(year, month, day, number, secret_enabled));
	    return "modification_form";
	}
}
