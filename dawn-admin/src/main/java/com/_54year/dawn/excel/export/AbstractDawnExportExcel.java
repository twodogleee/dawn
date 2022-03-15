package com._54year.dawn.excel.export;

import com._54year.dawn.excel.entity.ExcelDemo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
	abstract public <T> List<T> handleData(DawnExportExcelBasicParam param);

}
