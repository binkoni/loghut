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

package company.gonapps.loghut.dto;

import company.gonapps.loghut.exception.InvalidTagNameException;

public class TagDto {
	
	private String name;
	private PostDto post;
	
    public String getName() {
    	return name;
    }
    
    public PostDto getPost() {
    	return post;
    }
    
    public String getLocalPath() {
    	return getPath()
		+ "/"
		+ String.format("%04d", post.getYear())
		+ "/"
		+ String.format("%02d", post.getMonth())
		+ "/"
        + String.format("%02d", post.getDay())
        + "_"
        + post.getNumber()
        + ".html"
        + (post.getSecretEnabled() ? "s" : "");
    }
    
	public String getPath() { 
		return "/" + getName();
	}
    
    public TagDto setName(String name) throws InvalidTagNameException{
    	if(name.matches("\\s*")) throw new InvalidTagNameException();
    	this.name = name;
    	return this;
    }
    
    public TagDto setPost(PostDto post) {
    	this.post = post;
    	return this;
    }
}
