package company.gonapps.loghut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController extends BaseController {
	
    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
    	return "index";
    }
}
