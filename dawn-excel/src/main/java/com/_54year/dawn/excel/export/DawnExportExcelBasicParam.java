package com._54year.dawn.excel.export;

import lombok.Data;

/**
 * 导出excel基础查询参数
 * 主要用于导出时查询分页处理 所有导出的查询参数需要继承该类
 * 则可以做到统一 通用
 *
 * @author Andersen
 */
@Data
public class DawnExportExcelBasicParam {
	/**
	 * 当前页码
	 */
	private int pageNum;
	/**
	 * 当前页面大小
	 */
	private int pageSize;
}
