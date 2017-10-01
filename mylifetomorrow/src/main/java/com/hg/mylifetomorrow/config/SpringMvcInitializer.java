package com.hg.mylifetomorrow.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { MLTConfiguration.class };
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	private void registerCXFServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic cxfServlet = servletContext.addServlet(
				"cxf", new CXFServlet());
		cxfServlet.addMapping("/services/*");
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		super.onStartup(servletContext);
		registerCXFServlet(servletContext);
	}
	
	private WebApplicationContext createWebAppContext() {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(CxfConfig.class);
        return appContext;
    }

}
