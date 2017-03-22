package com.yht.findfriend.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;

public class AccountUtil {

	public static String md5(String user_pwd){
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = user_pwd.getBytes();
			byte[] output = md.digest(input);
			result = Base64.encodeBase64String(output);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("密码加密失败！！！");
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String str = "123qwe";
		System.out.println(md5(str));
	}
	
}
