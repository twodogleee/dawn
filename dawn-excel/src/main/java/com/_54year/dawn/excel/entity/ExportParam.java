package com._54year.dawn.excel.entity;

import lombok.Data;

/**
 * 导出 查询参数
 *
 * @param <P> 查询条件
 * @author Andersen
 */
@Data
public class ExportParam<P> {
	/**
	 * 当前页码
	 */
	private int pageNum;
	/**
	 * 当前页面大小
	 */
	private int pageSize;
	/**
	 * 查询条件
	 */
	private P queryParam;

	@Override
	public String toString() {
		return "ExportParam{" +
			"pageNum=" + pageNum +
			", pageSize=" + pageSize +
			", queryParam=" + queryParam +
			'}';
	}
}
