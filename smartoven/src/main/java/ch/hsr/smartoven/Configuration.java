package ch.hsr.smartoven;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private String mqttBroker;
	private String clientId;
	
	public String getMqttBroker(){
		return mqttBroker;
	}
	
	public String getClientId(){
		return clientId;
	}
	
	public Configuration(){
		try {
			this.getConfigurationFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getConfigurationFromFile() throws IOException {
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
			mqttBroker = prop.getProperty("mqttBroker", "tcp://127.0.0.1:1883");
			clientId = prop.getProperty("clientId", "SmartOven");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if(inputStream != null) inputStream.close();
		}
	}
	
}
