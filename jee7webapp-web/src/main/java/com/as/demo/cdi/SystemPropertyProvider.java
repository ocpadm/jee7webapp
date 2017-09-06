package com.as.demo.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class SystemPropertyProvider {

	@Produces
	@SystemProperty("")
	String findProperty(InjectionPoint ip) {
		SystemProperty annotation = ip.getAnnotated().getAnnotation(SystemProperty.class);

		String name = annotation.value();
		String found = System.getProperty(name);
		if (found == null) {
			throw new IllegalArgumentException("System property '" + name + "' is not defined!");
		}
		return found;
	}

}