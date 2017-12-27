package com.cjrj.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {
	private static final Base64 base64 = new Base64(true);
	/**
     * 获取当前session对象. 若当前线程不是web请求或当前尚未创建{@code session}则返回{@code null}
     * @return 返回当前对象或{@code null}
     */
    public static HttpSession currentSession() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        return attrs.getRequest().getSession(false);
    }
    
	 /**
     * 取出之前保存的请求
     * @return
     */
    public static String retrieveSavedRequest() {
        HttpSession session = currentSession();
        if (session == null) {
            return Const.REDIRECT_HOME;
        }
        String HashedlastPage = (String) session.getAttribute(Const.LAST_PAGE);
        if (HashedlastPage == null) {
        	return Const.REDIRECT_HOME;
        } else {
            return retrieve(HashedlastPage);
        }
    }
    /**
     *  解密请求的页面
     * @param targetPage
     * @return
     */
    public static String retrieve(String targetPage) {
        byte[] decode = base64.decode(targetPage);
        try {
            String requestUri = new String(decode, "UTF-8");
            int i = requestUri.indexOf("/", 1);
            int index = i<0?0:i;
            return requestUri.substring(index);
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    


}
