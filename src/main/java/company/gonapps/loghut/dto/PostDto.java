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

import java.util.List;

public class PostDto {
	
	private int year;
	private int month;
	private int day;
	private int number;
	private String title;
	private boolean secretEnabled;
	private String text;
	private List<TagDto> tags;

	public PostDto() {}
		
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getTitle() {
		return title;
	}
	
    public boolean getSecretEnabled() {
		return secretEnabled;
	}
    
	public String getText() {
		return text;
	}
	
	public List<TagDto> getTags() {
		return tags;
	}
	
    public String getLocalPath() {
    	return "/" + String.format("%04d", year) 
    			+ "/" + String.format("%02d", month) 
				+ "/" + String.format("%02d", day) 
				+ "_" + number
				+ ".html"
				+ (secretEnabled ? "s" : "");
    }
	
	public String getPath() {
		if(! getSecretEnabled())
			return "/"
				+ String.format("%04d", getYear())
				+ "/"
				+ String.format("%02d", getMonth())
				+ "/"
				+ String.format("%02d", getDay())
				+ "_" 
				+ getNumber()
				+ ".html";
		return "/secret.do?year="
				+ getYear()
				+ "&month="
				+ getMonth()
				+ "&day="
				+ getDay()
				+ "&number="
				+ getNumber();
	}
	
	
	public String getModificationFormPath() {
		return "/modification_form.do?year="
	            + getYear()
	            + "&month="
	            + getMonth()
	            + "&day="
	            + getDay()
	            + "&number="
	            + getNumber()
	            + "&secret_enabled="
	            + getSecretEnabled();
	}
	
	public String getDeletePath() {
		return "/delete.do?year="
	           + getYear() 
	           + "&month=" 
	           + getMonth()
	           + "&day="
	           + getDay()
	           + "&number="
	           + getNumber()
	           + "&secret_enabled="
	           + getSecretEnabled();
	}

	
	public PostDto setYear(int year) {
		this.year = year;
		return this;
	}
	
	public PostDto setMonth(int month) {
		this.month = month;
		return this;
	}
	
	public PostDto setDay(int day) {
		this.day = day;
		return this;
	}
	
	public PostDto setNumber(int number) {
		this.number = number;
		return this;
	}
	
	public PostDto setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public PostDto setSecretEnabled(boolean secretEnabled) {
		this.secretEnabled = secretEnabled;
		return this;
	}
	
	public PostDto setText(String text) {
		this.text = text;
		return this;
	}
	
	public PostDto setTags(List<TagDto> tags) {
		this.tags = tags;
		for(TagDto tag : tags) {
			tag.setPost(this);
		}
		return this;
	}
}
