package company.gonapps.loghut.filter;

import java.util.regex.Pattern;

import company.gonapps.loghut.dto.PostDto;

public class TextFilter implements Filter<PostDto> {
	
    Pattern textPattern;
    
	public TextFilter(String text) {
    	textPattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
    }
	
	@Override
	public boolean test(PostDto post) {
		return textPattern.matcher(post.getTitle()).find();
	}
}
