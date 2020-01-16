package com.rabo.customer.statement.processor.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class StatementProcessorInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext inContainer) throws ServletException {
		
		AnnotationConfigWebApplicationContext theAppContext = new AnnotationConfigWebApplicationContext();
		theAppContext.register(StatementProcessorConfigurer.class);
		theAppContext.setServletContext(inContainer);
 
        ServletRegistration.Dynamic servlet = inContainer.addServlet("dispatcher", new DispatcherServlet(theAppContext));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
	}

}
