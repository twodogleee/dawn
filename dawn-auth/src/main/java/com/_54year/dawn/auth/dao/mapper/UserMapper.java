package com._54year.dawn.auth.dao.mapper;

import com._54year.dawn.auth.entity.DawnUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户信息数据处理
 *
 * @author Andersen
 */
public interface UserMapper extends BaseMapper<DawnUser> {

	List<DawnUser> selectUserList();

	/**
	 * 查询用户
	 *
	 * @param userName 用户名
	 * @return 用户信息
	 */
	DawnUser selectUser(String userName);
}
