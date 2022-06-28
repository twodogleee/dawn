package com._54year.dawn.excel.task.impl;

import com._54year.dawn.excel.entity.ExportParam;
import com._54year.dawn.excel.export.AbstractDawnExportExcel;
import com._54year.dawn.excel.task.DawnCache;
import com._54year.dawn.excel.task.DawnTaskObserver;
import com._54year.dawn.excel.task.DawnTaskSubject;

/**
 * 需要导出数据处理主题
 *
 * @author Andersen
 */
public class ExportDataTaskSubject<T> implements DawnTaskSubject {


	/**
	 * 导出数据查询参数 主要用于分页
	 */
	private final ExportParam<?> param;
	/**
	 * 实现类
	 */
	private final AbstractDawnExportExcel service;

	/**
	 * 观察者 回调实现类
	 */
	private final DawnTaskObserver<T> dawnTaskObserver;

	/**
	 * 缓存
	 */
	private final DawnCache<T> dawnCache;

	/**
	 * 当前任务执行状态
	 */
	private State state;


	public ExportDataTaskSubject(ExportParam<?> param, AbstractDawnExportExcel service, DawnTaskObserver<T> dawnTaskObserver, DawnCache<T> dawnCache) {
		this.param = param;
		this.service = service;
		this.dawnTaskObserver = dawnTaskObserver;
		this.dawnCache = dawnCache;
	}

	@Override
	public void run() {
		if (dawnTaskObserver == null || dawnCache == null || !dawnCache.getExecuteFlag()) {
			return;
		}
		//开始
		this.state = State.STARTED;
		dawnTaskObserver.onStart(Thread.currentThread(), param);
		try {
			//执行中
			this.state = State.RUNNING;
			dawnTaskObserver.onRunning(Thread.currentThread(), param);

			// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
			T data = service.handleData(param);
			//完成
			this.state = State.DONE;
			this.dawnTaskObserver.onFinish(Thread.currentThread(), data, param, dawnCache);
		} catch (Exception e) {
			//错误
			this.state = State.ERROR;
			this.dawnTaskObserver.onError(Thread.currentThread(), e, param, dawnCache);
		}
	}


	@Override
	public State getSate() {
		return this.state;
	}

	@Override
	public void start() {

	}

	@Override
	public void interrupt() {

	}


}
