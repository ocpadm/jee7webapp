package com.as.demo.cdi;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class ApplicationPropertyProvider {

	private static Properties properties;

	@Produces
	@ApplicationProperty("")
	String findProperty(InjectionPoint ip) {
		ApplicationProperty annotation = ip.getAnnotated().getAnnotation(ApplicationProperty.class);
		String name = annotation.value();

		if (properties == null) {

			File f = new File(System.getProperty("jboss.server.config.dir") + File.separator + "jee7webapp-web",
			                "configuration.properties");

			try {

				FileInputStream fi = new FileInputStream(f);

				properties = new Properties();

				properties.load(fi);

				fi.close();

			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("Application property '" + name + "' is not defined!");
			}
		}

		String found = properties.getProperty(name);
		if (found == null) {
			throw new IllegalArgumentException("Application property '" + name + "' is not defined!");
		}
		return found;

	}

}