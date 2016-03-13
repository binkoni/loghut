package company.gonapps.loghut.filter;

import java.util.LinkedList;
import java.util.List;

import company.gonapps.loghut.dto.PostDto;

public class DaysFilter implements Filter<PostDto> {
	
    List<Integer> days;
    
	public DaysFilter(List<String> days) {
		this.days = new LinkedList<>();
		for(String day : days)
    	    this.days.add(new Integer(day));
    }
	
	@Override
	public boolean test(PostDto post) {
		int postDay = post.getDay();
		for(int day : days)
			if(postDay == day) return true;
		return false;
	}
}
