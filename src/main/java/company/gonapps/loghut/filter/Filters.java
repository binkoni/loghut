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

public class Filters<T> implements Filter<T> {
	
	private List<Filter<T>> filters;
	
	public Filters() {
		filters = new LinkedList<>();
	}
	
	public Filters(List<Filter<T>> filters) {
        this.filters = filters;		
	}
	
	public Filters<T> add(int index, Filter<T> filter) {
		filters.add(index, filter);
		return this;
	}
	
	public Filters<T> prepend(Filter<T> filter) {
		filters.add(0, filter);
		return this;
	}
	
	public Filters<T> append(Filter<T> filter) {
		filters.add(filter);
		return this;
	}
	
	
	@Override
    public boolean test(T target) {
		for(Filter<T> filter : filters) {
			if(! filter.test(target)) return false;
		}
        return true;
    }
}
