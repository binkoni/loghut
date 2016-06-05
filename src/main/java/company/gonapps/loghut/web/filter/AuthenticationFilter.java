/**
* LogHut is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* LogHut is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with LogHut.  If not, see <http://www.gnu.org/licenses/>.
**/

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