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
