package company.gonapps.loghut.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginFormController extends BaseController {
	
	@RequestMapping(value = "/login_form.do", method = RequestMethod.GET)
	public String loginForm(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null) {
		    return "login_form";
		}
		return "redirect:" + getSettingDao().getSetting("admin.url") + "/index.do";
	}
}
