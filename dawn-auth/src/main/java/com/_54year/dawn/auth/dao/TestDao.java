package com._54year.dawn.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface TestDao {
	@Select("select * from dawn_user where user_id = '123456'")
	Map<String, Object> test();
}
