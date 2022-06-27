package com._54year.dawn.excel.task;

/**
 * 抽象的线程观察者
 * 即观察到主题反生改变执行具体的回调
 *
 * @author Andersen
 */
public interface DawnTaskObserver<T> {
	/**
	 * 准备执行
	 *
	 * @param thread 执行线程
	 * @param param  参数
	 */
	void onStart(Thread thread, Object param);

	/**
	 * 执行中
	 *
	 * @param thread 执行线程
	 * @param param  执行参数
	 */
	void onRunning(Thread thread, Object param);

	/**
	 * 执行完成
	 *
	 * @param thread 执行线程
	 * @param result 执行结果
	 * @param param  执行参数
	 */
	void onFinish(Thread thread, T result, Object param);

	/**
	 * 执行错误
	 *
	 * @param thread 执行线程
	 * @param e      执行异常
	 * @param param  执行参数
	 */
	void onError(Thread thread, Exception e, Object param);
}
