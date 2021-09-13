package com.qa.urlshortener.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

	public static Properties prop;
	public static String path = System.getProperty("user.dir");

	public TestBase() {

		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					path + "\\src\\main\\java\\com\\qa\\urlshortener\\config\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
