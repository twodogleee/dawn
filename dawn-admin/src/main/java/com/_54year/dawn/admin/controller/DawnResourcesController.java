package com._54year.dawn.admin.controller;


import com._54year.dawn.admin.entity.DawnResources;
import com._54year.dawn.admin.service.DawnResourcesService;
import com._54year.dawn.admin.service.DawnRoleResourcesService;
import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.core.enums.DawnSystemRoleEnum;
import com._54year.dawn.mysql.config.DawnPage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * dawn-资源表 前端控制器
 * </p>
 *
 * @author Andersen
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/admin/dawnResources")

public class DawnResourcesController {
	@Autowired
	DawnResourcesService dawnResourcesService;

	@PostMapping("/list")
	@DawnResult
	@HasRole(roleName = "admin")
	public Object list(@RequestBody JSONObject param) {
		return dawnResourcesService.page(new DawnPage<>(param));
	}

	@PostMapping("/save")
	@DawnResult
	@HasRole(roleName = "admin")
	public Object save(@RequestBody DawnResources dawnResources) {
		return dawnResourcesService.save(dawnResources);
	}


}

