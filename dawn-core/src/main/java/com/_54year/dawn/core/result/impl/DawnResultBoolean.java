package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.DawnResultStrategy;

/**
 * boolean结果处理
 *
 * @author Andersen
 */
public class DawnResultBoolean implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		if ((boolean) result) {
			return DawnResult.success();
		}
		return DawnResult.failed(DawnBasicResultCode.BUSINESS_ERR, "操作失败");
	}

	@Override
	public boolean isLoad(Object result) {
		return result instanceof Boolean;
	}
}
