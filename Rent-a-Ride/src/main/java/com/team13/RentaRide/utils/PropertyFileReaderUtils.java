package com.team13.RentaRide.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class PropertyFileReaderUtils {

	public static Properties getProperties() {

		try {
			File file = ResourceUtils.getFile("classpath:config.properties");
			InputStream input = new FileInputStream(file);
			System.out.println("input: " + input);
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);
			return prop;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
