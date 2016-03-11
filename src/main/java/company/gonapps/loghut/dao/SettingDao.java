package company.gonapps.loghut.dao;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Repository;

@Repository
public class SettingDao {
	
    private Properties properties;
    
    private SettingDao()  throws IOException {
    	properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/settings.properties"));
    }
    
    public String getSetting(String key) {
		return properties.getProperty(key);
	}
}
