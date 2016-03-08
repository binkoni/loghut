package company.gonapps.loghut.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request,
			String id, String password, String request_path) throws Exception {
		if(id.equals(getSettingDao().getSetting("admin.id")) &&
				password.equals(getSettingDao().getSetting("admin.password"))) {
			
		    request.getSession().setMaxInactiveInterval(new Integer(getSettingDao().getSetting("session.timeout")));
		    if(request_path != null)
		        return "redirect:" + getSettingDao().getSetting("admin.url") + request_path;
		    return "redirect:" + getSettingDao().getSetting("admin.url");
		    
		} 
		return "redirect:" + getSettingDao().getSetting("admin.url") + "/login_form.do";		
	}
}
