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

import java.util.LinkedList;
import java.util.List;

import company.gonapps.loghut.dto.PostDto;

public class MonthsFilter implements Filter<PostDto> {
	
    List<Integer> months;
    
	public MonthsFilter(List<String> months) {
		this.months = new LinkedList<>();
		for(String month : months)
    	    this.months.add(new Integer(month));
    }
	
	@Override
	public boolean test(PostDto post) {
		int postMonth = post.getMonth();
		for(int month : months)
			if(postMonth == month) return true;
		return false;
	}
}
