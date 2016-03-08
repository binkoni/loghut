package company.gonapps.loghut.dao;

import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.springframework.stereotype.Repository;

@Repository
public class SettingDao {
	
    private Properties properties;
    private Preferences preferences;
    
    private SettingDao()  throws IOException {
    	properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/settings.properties"));
        preferences = Preferences.userRoot().node("loghut");
    }
    
    public String getSetting(String key) {
		return preferences.get(key, properties.getProperty(key));
	}
}
