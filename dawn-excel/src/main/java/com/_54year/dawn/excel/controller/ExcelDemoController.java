package com._54year.dawn.excel.controller;


import com._54year.dawn.common.annotation.DawnResult;
import com._54year.dawn.excel.entity.ExcelDemo;
import com._54year.dawn.excel.export.DawnExportExcel;
import com._54year.dawn.excel.service.ExcelDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * excel导出测试 前端控制器
 * </p>
 *
 * @author Andersen
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/excelDemo")
public class ExcelDemoController {
	@Autowired
	private ExcelDemoService excelDemoService;
	@Autowired
	private DawnExportExcel dawnExportExcel;

	@DawnResult
	@RequestMapping("/save")
	public Object save() {
		List<ExcelDemo> excelDemoList = new ArrayList<>();
		for (int i = 100001; i <= 200000; i++) {
			ExcelDemo demo = new ExcelDemo();
			demo.setFullName("李二狗" + i);
			demo.setAge(i);
			demo.setEmail("05" + i + "@54year.com");
			demo.setAddress("爱斯达克拉丝机放得开了撒街坊邻居" + i);
			demo.setSign("jfalsjlsjgsnglnalkalkjfasj阿gas苦尽甘来卡机管理科撒娇鬼爱上了国家了感觉s三方个单身狗是大公司都阿斯达工地上孤独颂歌SD敢达刷怪都是是大概的手感撒大公司的");
			demo.setPhone("132123456" + i);
			excelDemoList.add(demo);
		}
		return excelDemoService.saveBatch(excelDemoList);
	}

	@DawnResult
	@RequestMapping("/exportExcel")
	public Object repeatedWrite() {
		dawnExportExcel.repeatedWrite();
		return true;
	}

}

