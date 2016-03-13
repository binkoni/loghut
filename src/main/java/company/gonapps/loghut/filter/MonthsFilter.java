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
