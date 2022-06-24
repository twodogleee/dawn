package com._54year.dawn.excel.task.impl;

import com._54year.dawn.excel.task.DawnTask;
import com._54year.dawn.excel.task.DawnTaskObserver;
import com._54year.dawn.excel.task.DawnTaskSubject;

/**
 * 基本的多线程任务主题实现
 *
 * @param <T> 直接结果类型
 * @author Andersen
 */
public class DawnTaskSubjectImpl<T> implements DawnTaskSubject, Runnable {
	/**
	 * 观察者 回调实现类
	 */
	private final DawnTaskObserver<T> dawnTaskObserver;
	/**
	 * 需要具体执行的任务
	 */
	private final DawnTask<T> dawnTask;

	/**
	 * 当前任务执行状态
	 */
	private State state;

	public DawnTaskSubjectImpl(DawnTaskObserver<T> dawnTaskObserver, DawnTask<T> dawnTask) {
		this.dawnTaskObserver = dawnTaskObserver;
		this.dawnTask = dawnTask;
	}

	/**
	 * 获取任务执行状态
	 *
	 * @return 执行状态
	 */
	@Override
	public State getSate() {
		return this.state;
	}

	/**
	 * 修改执行状态
	 *
	 * @param state  执行状态
	 * @param result 执行结果
	 * @param e      执行异常
	 */
	private void updateState(State state, T result, Exception e) {
		this.state = state;
		if (dawnTaskObserver == null) {
			return;
		}
		try {
			switch (state) {
				case STARTED:
					this.dawnTaskObserver.onStart(Thread.currentThread());
					break;
				case RUNNING:
					this.dawnTaskObserver.onRunning(Thread.currentThread());
					break;
				case DONE:
					this.dawnTaskObserver.onFinish(Thread.currentThread(), result);
					break;
				case ERROR:
				default:
					this.dawnTaskObserver.onError(Thread.currentThread(), e);
			}
		} catch (Exception exception) {
			if (state == State.ERROR) {
				throw exception;
			}
		}
	}

	@Override
	public void start() {

	}

	@Override
	public void interrupt() {

	}

	/**
	 * 多线程任务具体执行方法
	 */
	@Override
	public void run() {
		//开始执行
		this.updateState(State.STARTED, null, null);
		try {
			//执行中
			this.updateState(State.RUNNING, null, null);
			T result = this.dawnTask.call();
			//完成
			this.updateState(State.DONE, result, null);
		} catch (Exception e) {
			//执行异常
			this.updateState(State.ERROR, null, e);
		}
	}

}
