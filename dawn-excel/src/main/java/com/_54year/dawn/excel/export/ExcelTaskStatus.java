package com._54year.dawn.excel.export;

import org.apache.commons.lang3.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * excel任务处理状态
 *
 * @author Andersen
 */
public class ExcelTaskStatus {
	/**
	 * 失败标识
	 */
	private final ConcurrentHashMap<String, String> statusMap;

	private final String FLAG_KEY = "errorFlag";

	private final String ERR_PAGE_KEY = "errorPageNum";

	public ExcelTaskStatus() {
		statusMap = new ConcurrentHashMap<>();
		statusMap.put(FLAG_KEY, "false");
	}

	public void setErrorFlag(Boolean flag, Integer num) {
		if (ObjectUtils.isEmpty(flag) || ObjectUtils.isEmpty(num)) {
			return;
		}
		statusMap.put(FLAG_KEY, flag.toString());
		statusMap.put(ERR_PAGE_KEY, num.toString());
	}

	/**
	 * 检测是否有其他线程错误 是否可继续执行
	 */
	public boolean checkTaskError(int nowPageNum) {
		boolean flag = Boolean.parseBoolean(statusMap.get(FLAG_KEY));
		if (flag) {
			int errNum = Integer.parseInt(statusMap.get(ERR_PAGE_KEY));
			if (nowPageNum < errNum) {
				flag = false;
			}
		}
		//如果其他线程执行错误 则退出循环等待
		return flag;
	}



}
