package com._54year.dawn.core.result;

import com._54year.dawn.core.result.impl.DawnResult;

/**
 * 结果封装策略
 *
 * @author Andersen
 */
public interface DawnResultStrategy {
	/**
	 * 结果处理
	 *
	 * @param result 返回结果
	 * @return 封装后的返回结果
	 */
	DawnResult load(Object result);

	/**
	 * 是否可处理
	 *
	 * @param result 返回结果
	 * @return true or false
	 */
	boolean isLoad(Object result);
}
