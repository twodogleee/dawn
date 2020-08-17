package com._54year.dawn.core.result;

import com._54year.dawn.core.result.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取适配的结果处理对象
 *
 * @author Andersen
 */
public class ResultReaderFactory {

	/**
	 * 返回处理类集合
	 */
	private static final List<DawnResultStrategy> RESULT_LIST = new ArrayList();

	static {
		RESULT_LIST.add(new DawnResultCollection());
		RESULT_LIST.add(new DawnResultMap());
		RESULT_LIST.add(new DawnResultString());
		RESULT_LIST.add(new DawnResultBoolean());
		RESULT_LIST.add(new DawnResultArray());
		RESULT_LIST.add(new DawnResultBasic());
	}

	/**
	 * 获取返回处理类
	 *
	 * @param result 返回结果
	 * @return 处理类
	 */
	public static DawnResultStrategy getResultReader(Object result) {
		for (DawnResultStrategy strategy : RESULT_LIST) {
			if (strategy.isLoad(result)) {
				return strategy;
			}
		}
		return null;
	}
}
