package com._54year.dawn.excel.entity;

import com._54year.dawn.excel.export.DawnExportExcelBasicParam;
import lombok.Data;

@Data
public class ExcelDemoReq extends DawnExportExcelBasicParam {
	private String fullName;
}
