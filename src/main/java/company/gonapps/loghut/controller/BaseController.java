package company.gonapps.loghut.controller;

import org.springframework.beans.factory.annotation.Autowired;

import company.gonapps.loghut.dao.SettingDao;

public class BaseController {
	
	private SettingDao settingDao;

	@Autowired
	public void setSettingDao(SettingDao settingDao) {
		this.settingDao = settingDao;
	}
	
	public SettingDao getSettingDao() {
		return settingDao;
	}

}
