package com._54year.dawn.mysql.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class DawnPage<T> extends Page<T> {
	private static final long serialVersionUID = 1L;

	public DawnPage(long current, long size) {
		super(current, size);
	}

	/**
	 * 查询参数
	 *
	 * @param queryParam 查询参数
	 */
	public DawnPage(JSONObject queryParam) {
		super(queryParam.getLongValue("pageIndex"), queryParam.getLongValue("pageSize"));
	}

}
