package com.cjrj.util;

import org.springframework.context.ApplicationContext;

/**
 * spring�����Ĺ���
 */
public class SpringContextHolder {
	
	public static   ApplicationContext webAppContext = null;

	/**
	 * ����spring BeanName��ȡϵͳ������Bean���� .
	 * @param beanName bean�����
	 * @return ʵ�����
	 */
	public static <T> T getContextBean(String beanName){
		return (T)webAppContext.getBean(beanName);
	}
	public static <T> T getContextBean(Class<T> beanName){
		return webAppContext.getBean(beanName);
	}
}
