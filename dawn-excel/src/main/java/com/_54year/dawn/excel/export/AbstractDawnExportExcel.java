package com._54year.dawn.excel.export;

import com._54year.dawn.excel.entity.ExportParam;

/**
 * 导出Excel抽象类
 *
 * @author Andersen
 */
public abstract class AbstractDawnExportExcel {
	/**
	 * 数据处理
	 *
	 * @param param
	 * @param <T>
	 * @return
	 */
	abstract public <T> T handleData(ExportParam param);

	/**
	 * 获取最大条目数
	 *
	 * @return 最大条目数
	 */
	abstract public int getTotal();

}
