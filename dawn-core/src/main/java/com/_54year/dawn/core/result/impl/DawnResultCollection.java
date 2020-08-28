package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.DawnResultStrategy;

import java.util.Collection;

/**
 * 集合类型 结果处理
 *
 * @author Andersen
 */
public class DawnResultCollection implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		if (result == null || ((Collection) result).isEmpty()) {
			return DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, "没有更多数据了");
		}
		return DawnResult.success(result);
	}

	@Override
	public boolean isLoad(Object result) {
		return result instanceof Collection;
	}
}
