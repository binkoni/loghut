package company.gonapps.loghut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.service.PostService;

@Controller
public class SecretController extends BaseController {
    private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

    @RequestMapping(value = "/secret.do", method = RequestMethod.GET)
    public String secret(ModelMap modelMap, int year, int month, int day, int number) throws Exception {
    	
        modelMap.put("post", postService.get(year, month, day, number, true));
    	return "secret";
    }
}
