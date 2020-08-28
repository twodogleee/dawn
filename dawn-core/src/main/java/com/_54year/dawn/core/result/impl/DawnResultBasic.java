package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.DawnResultStrategy;

/**
 * 基础结果处理 用来兜底
 *
 * @author Andersen
 */
public class DawnResultBasic implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		if (result == null) {
			DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, "请求结果为空");
		}
		return DawnResult.success(result);
	}

	@Override
	public boolean isLoad(Object result) {
		return true;
	}
}
