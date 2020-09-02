package com._54year.dawn.auth.service.impl;

import com._54year.dawn.auth.dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 创建验证用户
 *
 * @author Andersen
 */
@Service
public class DawnUserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DawnUserDetailsServiceImpl.class);



	/**
	 * 用户相关数据处理
	 */
	@Autowired
	private UserMapper userMapper;


	/**
	 * 根据用户名加载用户信息
	 *
	 * @param userName 用户名
	 * @return 用户信息
	 * @throws UsernameNotFoundException 未查询到用户
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		String testUser = "Andersen";
//		if (!testUser.equals(userName)) {
//			throw new UsernameNotFoundException("用户或密码错误!");
//		}
//		return new User(userName, passwordEncoder.encode("123456"),
//			AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
//		LOGGER.info(">>>>>密码:{}", passwordEncoder.encode("123456"));
		return userMapper.selectUser(userName);
	}

}
