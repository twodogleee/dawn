package com._54year.dawn.excel.export.imp;

import com._54year.dawn.excel.entity.ExcelDemo;
import com._54year.dawn.excel.export.AbstractDawnExportExcel;
import com._54year.dawn.excel.export.DawnExcelConstants;
import com._54year.dawn.excel.export.DawnExportExcelBasicParam;
import com._54year.dawn.excel.service.ExcelDemoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(DawnExcelConstants.EXCEL_DEMO)
public class ExportDawnDemoImpl extends AbstractDawnExportExcel {
	/**
	 * 注入
	 */
	@Autowired
	private ExcelDemoService excelDemoService;

	@Override
	public <T> List<T> handleData(DawnExportExcelBasicParam param) {
		LambdaQueryWrapper<ExcelDemo> qw = Wrappers.lambdaQuery();
		int pageIndex = (param.getPageNum() - 1) * param.getPageSize() + 1;
		qw.last("limit " + pageIndex + "," + param.getPageSize());
		List<ExcelDemo> dataList = excelDemoService.list(qw);
		return (List<T>) dataList;
	}

	@Override
	public int getTotal() {
		return 5000;
	}


}
