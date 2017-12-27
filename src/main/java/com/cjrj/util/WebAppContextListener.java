package com.cjrj.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 *  spring上下文监听器.
 */
public class WebAppContextListener implements ServletContextListener {
	/**
	 * <p>上下午环境销毁 .</p> 
	 *   
	 * @param event  事件
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
	}
	/**
	 * <p>上下午环境初始化 .</p>   
	 * 
	 * @param event 事件  
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public  void contextInitialized(ServletContextEvent event) {
		initWebContext(event);
	}
	public void initWebContext(ServletContextEvent event){
		SpringContextHolder.webAppContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

}
