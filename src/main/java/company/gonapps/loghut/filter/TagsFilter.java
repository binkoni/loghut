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

package company.gonapps.loghut.filter;

import java.util.List;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;

public class TagsFilter implements Filter<PostDto> {
	
    List<String> tagNames;
    
	public TagsFilter(List<String> tagNames) {
		this.tagNames = tagNames;
    }
	
	@Override
	public boolean test(PostDto post) {
		for(TagDto tag : post.getTags()) {
			String postTagName = tag.getName();
			for(String tagName : tagNames)
				if(postTagName.equals(tagName)) return true;
		}
		return false;
	}
}
