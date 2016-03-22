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

package company.gonapps.loghut.comparator;

import java.util.Comparator;

import company.gonapps.loghut.dto.PostDto;

public class PostDtoComparator implements Comparator<PostDto> {
	@Override
	public int compare(PostDto post1, PostDto post2) {
	    int yearDiff;
	    if((yearDiff = post1.getYear() - post2.getYear()) != 0) {
	    	return yearDiff * -1;
	    }
	    
	    int monthDiff;
	    if((monthDiff = post1.getMonth() - post2.getMonth()) != 0) {
	    	return monthDiff * -1;
	    }
	    
	    int dayDiff;
	    if((dayDiff = post1.getDay() - post2.getDay()) != 0) {
	    	return dayDiff * -1;
	    }
	    
	    return (post1.getNumber() - post2.getNumber()) * -1;
	}

}
