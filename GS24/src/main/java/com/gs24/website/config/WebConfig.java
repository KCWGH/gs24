package com.gs24.website.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	// root application context(Root WebApplicationContext)
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfig.class }; // RootConfig
	}

	// servlet application context(Servlet WebApplicationContext)
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletConfig.class }; // ServletConfig 
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}

} // end WebConfig
