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
	 */
	void onStart(Thread thread);

	/**
	 * 执行中
	 *
	 * @param thread 执行线程
	 */
	void onRunning(Thread thread);

	/**
	 * 执行完成
	 *
	 * @param thread 执行线程
	 * @param result 执行结果
	 */
	void onFinish(Thread thread, T result);

	/**
	 * 执行错误
	 *
	 * @param thread 执行线程
	 * @param e      执行异常
	 */
	void onError(Thread thread, Exception e);
}
