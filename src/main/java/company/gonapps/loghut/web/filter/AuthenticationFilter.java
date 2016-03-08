package company.gonapps.loghut.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter{
	
    @SuppressWarnings("unused")
	private FilterConfig filterConfig;
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
	        FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String requestPath = requestURI.substring(contextPath.length());
		String queryString = request.getQueryString();
		String loginPath = "/login.do";
		String loginFormPath = "/login_form.do";
		
		if(requestURI.equals(contextPath + loginFormPath)
				|| requestURI.equals(contextPath + loginPath)
				|| requestPath.equals("")
				|| session != null) {
			filterChain.doFilter(request, response);
		} else {
			request.setAttribute("requestPath", requestPath + (queryString != null ? "?" + queryString : ""));
			request.getRequestDispatcher(loginFormPath).forward(request, response);
		}
	}

	@Override
	public void destroy() {}
}
