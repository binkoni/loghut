package company.gonapps.loghut.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import company.gonapps.loghut.service.PostService;

@Controller
public class BackupController extends BaseController {
	
	private PostService postService;
	
	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService; 
	}
	
	@RequestMapping(value = "/backup.do", method = RequestMethod.GET)
    public void backup(HttpServletResponse response) throws Exception {
		response.setHeader("Content-Type", "application/x-gzip");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "BACKUP.tar.gz" + "\"");
		postService.backup(response.getOutputStream());
	}
}