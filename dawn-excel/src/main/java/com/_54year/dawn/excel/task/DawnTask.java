package com._54year.dawn.excel.task;

/**
 * 线程执行的逻辑
 *
 * @param <T> 执行结果
 * @author Andersen
 */
@FunctionalInterface
public interface DawnTask<T> {

	/**
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	T call(Object param) throws Exception;
}
