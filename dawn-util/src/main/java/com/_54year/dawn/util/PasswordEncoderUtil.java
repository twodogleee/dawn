package com._54year.dawn.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("nacos"));
	}
}
