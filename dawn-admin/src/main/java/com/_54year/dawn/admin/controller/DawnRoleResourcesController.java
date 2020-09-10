package com._54year.dawn.admin.controller;


import com._54year.dawn.admin.entity.DawnRoleResources;
import com._54year.dawn.admin.service.DawnRoleResourcesService;
import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.mysql.config.DawnPage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * dawn-角色与资源对应表 前端控制器
 * </p>
 *
 * @author Andersen
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/admin/dawnRoleResources")
public class DawnRoleResourcesController {
	@Autowired
	DawnRoleResourcesService dawnRoleResourcesService;

	@PostMapping("/list")
	@DawnResult
	@HasRole("admin")
	public Object list(@RequestBody JSONObject param) {
		return dawnRoleResourcesService.page(new DawnPage<>(param));
	}

	@PostMapping("/save")
	@DawnResult
	@HasRole("admin")
	public Object save(@RequestBody DawnRoleResources dawnRoleResources) {
		return dawnRoleResourcesService.save(dawnRoleResources);
	}

	@PostMapping("/update")
	@DawnResult
	@HasRole("admin")
	public Object update(@RequestBody DawnRoleResources dawnRoleResources) {
		return dawnRoleResourcesService.updateById(dawnRoleResources);
	}

	@PostMapping("/delete")
	@DawnResult
	@HasRole("admin")
	public Object delete(@RequestBody DawnRoleResources dawnRoleResources) {
		return dawnRoleResourcesService.removeById(dawnRoleResources.getId());
	}




}

