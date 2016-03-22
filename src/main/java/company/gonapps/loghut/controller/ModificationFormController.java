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
