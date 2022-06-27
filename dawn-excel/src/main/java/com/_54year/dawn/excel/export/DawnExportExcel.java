package com._54year.dawn.excel.export;

import com._54year.dawn.core.excetion.DawnBusinessException;
import com._54year.dawn.excel.entity.ExportParam;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel实现
 *
 * @author Andersen
 */
@Service
@Slf4j
public class DawnExportExcel {

	@Resource(name = "handleDataExecutor")
	private ThreadPoolTaskExecutor handleDataExecutor;

	@Resource(name = "writeDataExecutor")
	private ThreadPoolTaskExecutor writeDataExecutor;
	/**
	 * 使用spring自动注入
	 */
	private final Map<String, AbstractDawnExportExcel> serviceMap;

	public DawnExportExcel(Map<String, AbstractDawnExportExcel> serviceMap) {
		this.serviceMap = serviceMap;
	}

	/**
	 * 获取实现类
	 *
	 * @param serviceName 实现服务名
	 * @return 实现类
	 */
	public AbstractDawnExportExcel getExportService(String serviceName) {
		AbstractDawnExportExcel service = serviceMap.get(serviceName);
		if (service == null) {
			throw new DawnBusinessException("系统异常,未获取服务代理");
		}
		return service;
	}

	/**
	 * 根据配置类导出Excel
	 *
	 * @param param       请求参数
	 * @param writeClass  easyExcel相关类
	 * @param serviceName 调用服务名
	 * @param <T>         实体类
	 */
	public <T> void exportExcelByClass(DawnExportExcelBasicParam param, Class<T> writeClass, String serviceName) {
		try {
			//任务状态
			ExcelTaskStatus taskStatus = new ExcelTaskStatus();
			//本地缓存
			DawnExportExcelCache<T> cache = new DawnExportExcelCache<>(10, taskStatus);
			//最大条目数
			int total = serviceMap.get(serviceName).getTotal();
			//页面大小
			int pageSize = param.getPageSize();
			//最大页数
			int maxPageNum = (total + pageSize - 1) / pageSize;
			//循环建立数据处理任务
			for (int i = 1; i <= maxPageNum; i++) {
				ExportParam nowPageParam = new ExportParam();
				nowPageParam.setPageNum(i);
				nowPageParam.setPageSize(pageSize);
				//执行数据处理任务
				handleDataExecutor.execute(new HandleDataTask<>(nowPageParam, serviceName, cache, taskStatus));
			}
			//执行写出Excel任务
			writeDataExecutor.execute(new WriteExcelTask<>(cache, taskStatus, maxPageNum, writeClass));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void repeatedWrite() {
		// 方法1 如果写到同一个sheet
//		String fileName = "D:\\workspace\\demo\\dawn\\dawn-admin\\src\\main\\resources\\" + System.currentTimeMillis() + ".xlsx";
//		ExcelWriter excelWriter = null;
		//			// 这里 需要指定写用哪个class去写
//			excelWriter = EasyExcel.write(fileName, ExcelDemo.class).build();
//			// 这里注意 如果同一个sheet只要创建一次
//			WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
		// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
//			JSONObject param = new JSONObject();
//			param.put("pageSize", 1000);
//			for (int i = 1; i <= 100; i++) {
//				param.put("page", (i - 1) * 10000 + 1);
//				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//				List<ExcelDemo> data = serviceMap.get(DawnExcelConstants.EXCEL_DEMO).handleData(param);
//				excelWriter.write(data, writeSheet);
//
//			}
//		finally {
//			// 千万别忘记finish 会帮忙关闭流
//			if (excelWriter != null) {
//				excelWriter.finish();
//			}
//		}


//		// 方法2 如果写到不同的sheet 同一个对象
//		fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
//		try {
//			// 这里 指定文件
//			excelWriter = EasyExcel.write(fileName, DemoData.class).build();
//			// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
//			for (int i = 0; i < 5; i++) {
//				// 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
//				WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
//				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//				List<DemoData> data = data();
//				excelWriter.write(data, writeSheet);
//			}
//		} finally {
//			// 千万别忘记finish 会帮忙关闭流
//			if (excelWriter != null) {
//				excelWriter.finish();
//			}
//		}
//
//		// 方法3 如果写到不同的sheet 不同的对象
//		fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
//		try {
//			// 这里 指定文件
//			excelWriter = EasyExcel.write(fileName).build();
//			// 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
//			for (int i = 0; i < 5; i++) {
//				// 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
//				WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoData.class).build();
//				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//				List<DemoData> data = data();
//				excelWriter.write(data, writeSheet);
//			}
//		} finally {
//			// 千万别忘记finish 会帮忙关闭流
//			if (excelWriter != null) {
//				excelWriter.finish();
//			}
//		}


	}

	/**
	 * 数据处理任务
	 *
	 * @param <T>
	 */
	class HandleDataTask<T> implements Runnable {
		/**
		 * 导出数据查询参数 主要用于分页
		 */
		private final ExportParam param;
		/**
		 * 实现类
		 */
		private final String serviceName;
		/**
		 * 导出数据缓存
		 */
		private final DawnExportExcelCache<T> dawnExportExcelCache;

		/**
		 * excel导出状态
		 */
		private final ExcelTaskStatus excelTaskStatus;

		public HandleDataTask(ExportParam param, String serviceName, DawnExportExcelCache<T> dawnExportExcelCache, ExcelTaskStatus excelTaskStatus) {
			this.param = param;
			this.serviceName = serviceName;
			this.dawnExportExcelCache = dawnExportExcelCache;
			this.excelTaskStatus = excelTaskStatus;
		}

		@Override
		public void run() {
			if (excelTaskStatus.checkTaskError(param.getPageNum())) {
				return;
			}
			try {
				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
				List<T> data = getExportService(serviceName).handleData(param);
				dawnExportExcelCache.savePageData(param.getPageNum(), data);
			} catch (Exception e) {
				excelTaskStatus.setErrorFlag(true, 5);
				log.error("导出异常", e);
			}
		}
	}

	/**
	 * 异步写出任务
	 *
	 * @param <T> 类型
	 */
	static class WriteExcelTask<T> implements Runnable {
		/**
		 * 导出数据缓存
		 */
		private final DawnExportExcelCache<T> dawnExportExcelCache;

		/**
		 * excel导出状态
		 */
		private final ExcelTaskStatus excelTaskStatus;
		/**
		 * 最大页码
		 */
		private final int maxPageNum;
		/**
		 * 写入类
		 */
		private final Class<T> writClass;

		public WriteExcelTask(DawnExportExcelCache<T> dawnExportExcelCache, ExcelTaskStatus excelTaskStatus, int maxPageNum, Class<T> writClass) {
			this.dawnExportExcelCache = dawnExportExcelCache;
			this.excelTaskStatus = excelTaskStatus;
			this.maxPageNum = maxPageNum;
			this.writClass = writClass;
		}

		@Override
		public void run() {
			String fileName = "D:\\workspace\\demo\\dawn\\dawn-excel\\src\\main\\resources\\" + System.currentTimeMillis() + ".xlsx";
			ExcelWriter excelWriter = null;
			long start = System.currentTimeMillis();
			try {
				// 这里 需要指定写用哪个class去写
				excelWriter = EasyExcel.write(fileName, writClass).build();
				// 这里注意 如果同一个sheet只要创建一次
				WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
				for (int i = 1; i <= maxPageNum; i++) {
					//如果线程出现异常则结束
					if (excelTaskStatus.checkTaskError(i)) {
						break;
					}
					List<T> data = dawnExportExcelCache.getPageData();
					log.info("===============当前写出" + i + "页================");
					excelWriter.write(data, writeSheet);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//执行完毕后清除本地缓存
				dawnExportExcelCache.clearCache();
				// 千万别忘记finish 会帮忙关闭流
				if (excelWriter != null) {
					excelWriter.finish();
				}
			}
			log.info("=====================写出完毕");
			log.info("===================导出耗时" + (System.currentTimeMillis() - start) + "毫秒");
		}
	}
}
