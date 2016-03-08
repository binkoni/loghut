package company.gonapps.loghut.dto;

import company.gonapps.loghut.exception.InvalidTagNameException;

public class TagDto {
	
	private String name;
	private PostDto post;
	
    public String getName() {
    	return name;
    }
    
    public PostDto getPost() {
    	return post;
    }
    
    public String getLocalPath() {
    	return getPath()
		+ "/"
		+ String.format("%04d", post.getYear())
		+ "/"
		+ String.format("%02d", post.getMonth())
		+ "/"
        + String.format("%02d", post.getDay())
        + "_"
        + post.getNumber()
        + ".html"
        + (post.getSecretEnabled() ? "s" : "");
    }
    
	public String getPath() { 
		return "/" + getName();
	}
    
    public TagDto setName(String name) throws InvalidTagNameException{
    	if(name.matches("\\s*")) throw new InvalidTagNameException();
    	this.name = name;
    	return this;
    }
    
    public TagDto setPost(PostDto post) {
    	this.post = post;
    	return this;
    }
}
