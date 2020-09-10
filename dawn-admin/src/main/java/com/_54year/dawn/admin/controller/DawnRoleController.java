package com._54year.dawn.admin.controller;


import com._54year.dawn.admin.entity.DawnRole;
import com._54year.dawn.admin.service.DawnRoleService;
import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.core.enums.DawnSystemRoleEnum;
import com._54year.dawn.mysql.config.DawnPage;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * dawn-角色表 前端控制器
 * </p>
 *
 * @author Andersen
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/admin/dawnRole")
public class DawnRoleController {

	@Autowired
	DawnRoleService dawnRoleService;

	/**
	 * 获取角色列表
	 *
	 * @return 角色列表
	 */
	@PostMapping("/list")
	@DawnResult
	@HasRole("admin")
	public Object list(@RequestBody JSONObject param) {
		Page<DawnRole> page = new DawnPage<>(param);
		return dawnRoleService.page(page);
	}

	/**
	 * 保存角色信息
	 *
	 * @param dawnRole 角色信息
	 * @return boolean
	 */
	@PostMapping("/save")
	@DawnResult
	@HasRole("admin")
	public Object save(@RequestBody DawnRole dawnRole) {
		return dawnRoleService.save(dawnRole);
	}

	/**
	 * 修改角色信息
	 *
	 * @param dawnRole 角色信息
	 * @return boolean
	 */
	@PostMapping("/update")
	@DawnResult
	@HasRole("admin")
	public Object update(@RequestBody DawnRole dawnRole) {
		if (checkSystemRole(dawnRole.getRoleId())) {
			return false;
		}
		return dawnRoleService.updateById(dawnRole);
	}

	/**
	 * 删除角色信息
	 *
	 * @param param id
	 * @return boolean
	 */
	@PostMapping("/delete")
	@DawnResult
	@HasRole("admin")
	public Object delete(@RequestBody JSONObject param) {
		String roleId = param.getString("roleId");
		if (checkSystemRole(roleId)) {
			return false;
		}
		return dawnRoleService.removeById(roleId);
	}

	/**
	 * 系统角色禁止修改
	 *
	 * @param roleId roleId
	 * @return boolean
	 */
	public boolean checkSystemRole(String roleId) {
		return DawnSystemRoleEnum.ADMIN.roleId().toString().equals(roleId)
			|| DawnSystemRoleEnum.USER.roleId().toString().equals(roleId);
	}


}

