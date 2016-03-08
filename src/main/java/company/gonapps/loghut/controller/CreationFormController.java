package company.gonapps.loghut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreationFormController extends BaseController {
	
	@RequestMapping(value = "/creation_form.do", method = RequestMethod.GET)
	public String creationForm(ModelMap modelMap) {
		return "creation_form";
	}
}
