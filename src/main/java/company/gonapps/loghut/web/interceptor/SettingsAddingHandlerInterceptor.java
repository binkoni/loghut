package company.gonapps.loghut.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import company.gonapps.loghut.dao.SettingDao;

public class SettingsAddingHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private SettingDao settingDao;
	
	@Autowired
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}
	
	@Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) {
		if(modelAndView != null)
			modelAndView.getModelMap().addAttribute("settings", settingDao);
	}


}
