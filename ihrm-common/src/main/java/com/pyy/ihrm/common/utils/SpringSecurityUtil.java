package com.pyy.ihrm.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SpringSecurityUtil {
	
	
	/**
	 * @description  加密
	 * @return
	 */
	public static String encoderPassword(String password){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	/**
	 * @description 校验
	 *  password 是明文
	 *  secret 是密钥
	 * @return
	 */
	public static boolean checkpassword(String password, String secret){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, secret);
	}


	public static void main(String[] args) {
		System.out.println(encoderPassword("123456"));
	}
}
