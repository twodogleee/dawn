package com._54year.dawn.excel.exception;

import com._54year.dawn.core.excetion.DawnBusinessException;

/**
 * excel导出异常
 *
 * @author Andersen
 */
public class DawnExportException extends DawnBusinessException {
	public DawnExportException() {
		super();
	}

	public DawnExportException(String message) {
		super(message);
	}

	public DawnExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public DawnExportException(Throwable cause) {
		super(cause);
	}

	protected DawnExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 数据处理异常
	 *
	 * @return 导出异常
	 */
	public static DawnExportException handleError() {
		return new DawnExportException("导出Excel时数据处理异常!");
	}
}
