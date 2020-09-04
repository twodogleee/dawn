package com._54year.dawn.gateway.dao;

import java.util.List;
import java.util.Map;

public interface DawnRoleResourcesMapper {

	/**
	 * 根据url查询到拥有该url权限角色集合
	 * @param url url
	 * @return 角色集合信息 {id,resources_url,role_id,role_name}
	 */
	List<Map<String,Object>> selectRoleListByUrl(String url);

}
