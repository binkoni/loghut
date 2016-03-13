package company.gonapps.loghut.filter;

import java.util.List;

import company.gonapps.loghut.dto.PostDto;
import company.gonapps.loghut.dto.TagDto;

public class TagsFilter implements Filter<PostDto> {
	
    List<String> tagNames;
    
	public TagsFilter(List<String> tagNames) {
		this.tagNames = tagNames;
    }
	
	@Override
	public boolean test(PostDto post) {
		for(TagDto tag : post.getTags()) {
			String postTagName = tag.getName();
			for(String tagName : tagNames)
				if(postTagName.equals(tagName)) return true;
		}
		return false;
	}
}
