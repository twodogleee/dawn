package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.DawnResultStrategy;

/**
 * 数据类型返回结果处理
 *
 * @author Andersen
 */
public class DawnResultArray implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		if (result == null) {
			return DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, "没有更多数据了");
		}
		return DawnResult.success(result);
	}

	@Override
	public boolean isLoad(Object result) {
		return result.getClass().isArray();
	}
}
