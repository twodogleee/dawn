package com._54year.dawn.excel.export;

import com._54year.dawn.excel.exception.DawnExportException;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * excel任务处理状态
 *
 * @author Andersen
 */
public class ExcelTaskStatus {
	/**
	 * 失败标识
	 */
	private final AtomicBoolean errorFlag;

	public ExcelTaskStatus() {
		this.errorFlag = new AtomicBoolean(true);
	}

	public void setErrorFlag(boolean flag) {
		this.errorFlag.set(flag);
	}

	/**
	 * 检测是否有其他线程错误 是否可继续执行
	 */
	public boolean checkTaskError() {
		//如果其他线程执行错误 则退出循环等待
		return !errorFlag.get();
	}
}
