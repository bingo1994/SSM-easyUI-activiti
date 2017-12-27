package com.cjrj.util;

import org.springframework.context.ApplicationContext;

/**
 * spring上下文管理
 */
public class SpringContextHolder {
	
	public static   ApplicationContext webAppContext = null;

	/**
	 * 根据spring BeanName获取系统上下文Bean对象 .
	 * @param beanName bean定义的
	 * @return 实体对象
	 */
	public static <T> T getContextBean(String beanName){
		return (T)webAppContext.getBean(beanName);
	}
	public static <T> T getContextBean(Class<T> beanName){
		return webAppContext.getBean(beanName);
	}
}
