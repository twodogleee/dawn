package com._54year.dawn.excel.task;

public interface DawnCache<D> {
	/**
	 * 缓存数据
	 *
	 * @param data 需要缓存的数据
	 * @param page 当前数据的页数
	 * @return 是否成功
	 */
	boolean addData(D data, int page);

	/**
	 * 获取缓存数据
	 *
	 * @return 缓存的数据
	 */
	D getData() throws Exception;

	/**
	 * 如果执行错误
	 */
	void onError();

	/**
	 * 清除队列缓存数据
	 */
	void clearQueue();

	/**
	 * 获取执行状态
	 *
	 * @return 执行状态
	 */
	boolean getExecuteFlag();
}
