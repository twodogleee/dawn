package com._54year.dawn.excel.task;

/**
 * 抽象多线程主题
 *
 * @author Andersen
 */
public interface DawnTaskSubject extends Runnable {
	/**
	 * 执行状态
	 */
	enum State {
		/**
		 * 准备执行
		 */
		STARTED,
		/**
		 * 执行中
		 */
		RUNNING,
		/**
		 * 执行完成
		 */
		DONE,
		/**
		 * 执行错误
		 */
		ERROR;
	}

	/**
	 * 获取执行状态
	 *
	 * @return 当前状态
	 */
	State getSate();

	/**
	 * 开始执行
	 */
	void start();

	/**
	 * 阻断执行
	 */
	void interrupt();
}
