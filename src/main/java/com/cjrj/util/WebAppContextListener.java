package com.cjrj.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 *  spring�����ļ�����.
 */
public class WebAppContextListener implements ServletContextListener {
	/**
	 * <p>�����绷������ .</p> 
	 *   
	 * @param event  �¼�
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
	}
	/**
	 * <p>�����绷����ʼ�� .</p>   
	 * 
	 * @param event �¼�  
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public  void contextInitialized(ServletContextEvent event) {
		initWebContext(event);
	}
	public void initWebContext(ServletContextEvent event){
		SpringContextHolder.webAppContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

}
