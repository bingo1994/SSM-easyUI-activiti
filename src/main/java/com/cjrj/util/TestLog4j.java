package com.cjrj.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
	private static final transient Logger log = Logger.getLogger(TestLog4j.class);

	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException,
			NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, NoSuchPaddingException {

		PropertyConfigurator.configure("src/log4j.properties");
		log.info("记录日志信息，将在控制台输出");
		log.error("可以记录错误信息，输出字体为控制");
	}
}
