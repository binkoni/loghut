package company.gonapps.loghut.filter;

import java.util.regex.Pattern;

import company.gonapps.loghut.dto.PostDto;

public class TitleFilter implements Filter<PostDto> {
	
    Pattern titlePattern;
    
	public TitleFilter(String title) {
    	titlePattern = Pattern.compile(title, Pattern.CASE_INSENSITIVE);
    }
	
	@Override
	public boolean test(PostDto post) {
		return titlePattern.matcher(post.getTitle()).find();
	}
}
