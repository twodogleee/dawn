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
	 * 执行方法
	 *
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	T call() throws Exception;
}
