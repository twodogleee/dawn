package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.DawnResultStrategy;

import java.util.Map;

/**
 * Map类型 结果处理
 */
public class DawnResultMap implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		if (result == null || ((Map) result).isEmpty()) {
			return DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, "没有更多数据了");
		}
		return DawnResult.success(result);
	}

	@Override
	public boolean isLoad(Object result) {
		return result instanceof Map;
	}
}
