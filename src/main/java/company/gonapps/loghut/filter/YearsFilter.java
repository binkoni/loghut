package company.gonapps.loghut.filter;

import java.util.LinkedList;
import java.util.List;

import company.gonapps.loghut.dto.PostDto;

public class YearsFilter implements Filter<PostDto> {
	
    List<Integer> years;
    
	public YearsFilter(List<String> years) {
		this.years = new LinkedList<>();
		for(String year : years)
    	    this.years.add(new Integer(year));
    }
	
	@Override
	public boolean test(PostDto post) {
		int postYear = post.getYear();
		for(int year : years)
			if(postYear == year) return true;
		return false;
	}
}
