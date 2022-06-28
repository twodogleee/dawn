package com._54year.dawn.excel.task.impl;

import com._54year.dawn.excel.entity.ExportParam;
import com._54year.dawn.excel.task.DawnCache;
import com._54year.dawn.excel.task.DawnTaskObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
	 * @param thread    执行线程
	 * @param result    执行结果
	 * @param param     执行参数
	 * @param dawnCache 缓存
	 */
	@Override
	public void onFinish(Thread thread, T result, Object param, DawnCache<T> dawnCache) {
		//添加到数据队列中
		try {
			//如果参数为空 则结束
			if (ObjectUtils.isEmpty(param)) {
				return;
			}
			ExportParam exportParam = (ExportParam) param;
			dawnCache.addData(result, exportParam.getPageNum());
		} catch (Exception e) {
			this.onError(thread, e, param, dawnCache);
		}

	}

	/**
	 * 执行错误
	 *
	 * @param thread    执行线程
	 * @param e         执行异常
	 * @param param     执行参数
	 * @param dawnCache 缓存
	 */
	@Override
	public void onError(Thread thread, Exception e, Object param, DawnCache<T> dawnCache) {
		//清理缓存
		dawnCache.onError();
		log.error(thread.getName() + ":执行错误!" + param.toString());
	}
}
