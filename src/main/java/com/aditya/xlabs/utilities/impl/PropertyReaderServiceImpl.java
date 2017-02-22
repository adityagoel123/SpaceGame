package com.aditya.xlabs.utilities.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.aditya.xlabs.utilities.IPropertyReaderService;

public class PropertyReaderServiceImpl implements IPropertyReaderService {

	String result = "";
	InputStream inputStream;
	
	public String readPropertyKey(String key) {
		try { 
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	 		if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 			if(null != key && key !="" && null != prop.getProperty(key)){
 				result = prop.getProperty(key);
 			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}


}

