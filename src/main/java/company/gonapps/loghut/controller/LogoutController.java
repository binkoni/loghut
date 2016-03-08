package company.gonapps.loghut.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController extends BaseController {
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		request.getSession(false).invalidate();
		return "redirect:" + getSettingDao().getSetting("admin.url") + "/login_form.do";
	}
}
