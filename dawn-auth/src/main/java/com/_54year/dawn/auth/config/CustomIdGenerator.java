package com._54year.dawn.auth.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
@Slf4j
//@Component
@Deprecated
public class CustomIdGenerator implements IdentifierGenerator {

	private final AtomicLong al = new AtomicLong(100000000);


	@Override
	public Long nextId(Object entity) {
		//可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
		String bizKey = entity.getClass().getName();
		log.info("bizKey:{}", bizKey);
		MetaObject metaObject = SystemMetaObject.forObject(entity);
		String name = (String) metaObject.getValue("name");
		final long id = al.getAndAdd(1);
		log.info("为{}生成主键值->:{}", name, id);
		return id;
	}
}
