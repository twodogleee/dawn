package com._54year.dawn.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 创建验证用户
 *
 * @author Andersen
 */
@Service
public class DawnUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		String testUser = "Andersen";
		if (!testUser.equals(userName)) {
			throw new UsernameNotFoundException("用户或密码错误!");
		}
		return new User(userName, passwordEncoder.encode("123456"),
			AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
