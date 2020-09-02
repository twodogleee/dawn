package com._54year.dawn.auth.service.impl;

import com._54year.dawn.auth.dao.mapper.DawnUserRoleMapper;
import com._54year.dawn.auth.dao.mapper.RoleMapper;
import com._54year.dawn.auth.dao.mapper.UserMapper;
import com._54year.dawn.auth.entity.DawnRole;
import com._54year.dawn.auth.entity.DawnUser;
import com._54year.dawn.auth.entity.DawnUserRole;
import com._54year.dawn.auth.service.UserService;
import com._54year.dawn.core.enums.DawnRoleEnum;
import com._54year.dawn.core.excetion.DawnBusinessException;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	/**
	 * 用户相关数据处理
	 */
	@Autowired
	private UserMapper userMapper;
	/**
	 * 加密类
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private IdentifierGenerator identifierGenerator;

	@Autowired
	private DawnUserRoleMapper dawnUserRoleMapper;

	/**
	 * 注册用户
	 *
	 * @param dawnUser 用户信息
	 * @return 是否注册成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean regist(DawnUser dawnUser) {
		if (userMapper.selectUser(dawnUser.getUsername()) != null) {
			throw new DawnBusinessException("用户名已注册!");
		}
		Number userId = identifierGenerator.nextId(dawnUser);
		dawnUser.setUserId(userId.toString());
		dawnUser.setPassword(passwordEncoder.encode(dawnUser.getPassword()));
		dawnUser.setCreateTime(LocalDateTime.now());
		dawnUser.setUpdateTime(LocalDateTime.now());
		dawnUser.setEnabled(true);
		log.info(">>>>>新注册用户信息:{}", dawnUser.toString());
		return
			userMapper.insert(dawnUser) != 0
				&&
				dawnUserRoleMapper.insert(new DawnUserRole(userId.toString(),DawnRoleEnum.USER.roleId().toString())) != 0;
	}


}
