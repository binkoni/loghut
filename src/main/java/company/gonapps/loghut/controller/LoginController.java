/**
* LogHut is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* LogHut is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with LogHut.  If not, see <http://www.gnu.org/licenses/>.
**/

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
