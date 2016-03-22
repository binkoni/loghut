package company.gonapps.loghut.controller;

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

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;
import company.gonapps.loghut.service.PostService;

@Controller
public class CreateController extends BaseController {
	
	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
    @RequestMapping(value = "/create.do", method = RequestMethod.POST)
    public String create(ModelMap modelMap, String title, boolean secret_enabled, String text, String tag_names) throws Exception {
    	PostDto post = new PostDto();
    	List<TagDto> tags = new LinkedList<>();

    	post.setTitle(title)
    	.setSecretEnabled(secret_enabled)
    	.setText(text);
    	
    	for(String tagName : tag_names.split("\\s*,\\s*")) {
    		if(tagName.matches("\\s*")) continue;
    		tags.add(new TagDto().setName(tagName));
    	}
    	
    	post.setTags(tags);  
    	
        postService.create(post);
        
        modelMap.put("post", post);
    	
        return "create";
    }
}
