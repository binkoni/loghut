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
