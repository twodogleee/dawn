package com._54year.dawn.excel.task.impl;

import com._54year.dawn.excel.task.DawnTaskObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * excel导出观察者
 *
 * @param <T> 数据类型
 * @author Andersen
 */
@Service
@Slf4j
public class ExcelExportObserver<T> implements DawnTaskObserver<T> {

	/**
	 * 准备执行
	 *
	 * @param thread 执行线程
	 * @param param  参数
	 */
	@Override
	public void onStart(Thread thread, Object param) {
		log.info(thread.getName() + ":开始执行!" + param.toString());
	}

	/**
	 * 执行中
	 *
	 * @param thread 执行线程
	 * @param param  执行参数
	 */
	@Override
	public void onRunning(Thread thread, Object param) {
		log.info(thread.getName() + ":执行中!" + param.toString());

	}

	/**
	 * 执行完成
	 *
	 * @param thread 执行线程
	 * @param result 执行结果
	 * @param param  执行参数
	 */
	@Override
	public void onFinish(Thread thread, T result, Object param) {
		//添加到数据队列中

	}

	/**
	 * 执行错误
	 *
	 * @param thread 执行线程
	 * @param e      执行异常
	 * @param param  执行参数
	 */
	@Override
	public void onError(Thread thread, Exception e, Object param) {
		log.error(thread.getName() + ":执行错误!" + param.toString());
	}
}
