package com._54year.dawn.core.result.impl;

import com._54year.dawn.core.result.DawnResultStrategy;

/**
 * 字符串类型 结果处理
 */
public class DawnResultString implements DawnResultStrategy {
	@Override
	public DawnResult load(Object result) {
		return DawnResult.success(result);
	}

	@Override
	public boolean isLoad(Object result) {
		return result instanceof String;
	}
}
