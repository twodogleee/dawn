package com._54year.dawn.excel.export;

import com._54year.dawn.core.excetion.DawnBusinessException;
import com._54year.dawn.excel.entity.ExcelDemo;
import com._54year.dawn.excel.entity.ExcelDemoReq;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 导出Excel实现
 *
 * @author Andersen
 */
@Service
@Slf4j
public class DawnExportExcel {

	@Resource(name = "asyncServiceExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
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

	public void repeatedWrite() {
		// 方法1 如果写到同一个sheet
//		String fileName = "D:\\workspace\\demo\\dawn\\dawn-admin\\src\\main\\resources\\" + System.currentTimeMillis() + ".xlsx";
//		ExcelWriter excelWriter = null;
		long start = System.currentTimeMillis();
		try {
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

			//按照页数分线程查询
			CountDownLatch countDownLatch = new CountDownLatch(10);
			List<FutureTask<Boolean>> futureTaskList = new ArrayList<>();
			long count = countDownLatch.getCount();
			ExcelDemoReq param = new ExcelDemoReq();
			param.setPageSize(1);
			DawnExportExcelCache<ExcelDemo> cache = new DawnExportExcelCache<>(10);
			for (int i = 1; i <= 10; i++) {
				param.setPageNum(i);
				FutureTask<Boolean> futureTask = new FutureTask<>(new ExportExcelCallable<>(param, countDownLatch, DawnExcelConstants.EXCEL_DEMO, cache));
				threadPoolTaskExecutor.execute(futureTask);
				futureTaskList.add(futureTask);
			}
			threadPoolTaskExecutor.execute(() -> {// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
				String fileName = "D:\\workspace\\demo\\dawn\\dawn-admin\\src\\main\\resources\\" + System.currentTimeMillis() + ".xlsx";
				ExcelWriter excelWriter = null;
				try {
					// 这里 需要指定写用哪个class去写
					excelWriter = EasyExcel.write(fileName, ExcelDemo.class).build();
					// 这里注意 如果同一个sheet只要创建一次
					WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
					for (int i = 1; i <= 10; i++) {
						List<ExcelDemo> data = cache.getPageData();
						log.info("===============" + data.toString());
						excelWriter.write(data, writeSheet);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 千万别忘记finish 会帮忙关闭流
					if (excelWriter != null) {
						excelWriter.finish();
					}
				}
				log.info("=====================写出完毕");
			});
			//等待所有线程执行完毕
//			countDownLatch.await(30, TimeUnit.SECONDS);
			//获取执行结果
//			for (FutureTask<Boolean> customerPageRespFutureTask : futureTaskList) {
//				System.out.println(customerPageRespFutureTask.get());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			// 千万别忘记finish 会帮忙关闭流
//			if (excelWriter != null) {
//				excelWriter.finish();
//			}
//		}
		log.info("===================导出耗时" + (System.currentTimeMillis() - start) + "毫秒");

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


	class ExportExcelCallable<T> implements Callable<Boolean> {
		/**
		 * 导出数据查询参数 主要用于分页
		 */
		private final DawnExportExcelBasicParam param;

		private final String serviceName;

		private final CountDownLatch countDownLatch;

		private final DawnExportExcelCache<T> dawnExportExcelCache;


		public ExportExcelCallable(DawnExportExcelBasicParam param, CountDownLatch countDownLatch, String serviceName, DawnExportExcelCache<T> dawnExportExcelCache) {
			this.param = param;
			this.countDownLatch = countDownLatch;
			this.serviceName = serviceName;
			this.dawnExportExcelCache = dawnExportExcelCache;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				log.info("=============" + LocalDateTime.now().toString() + "name=" + Thread.currentThread().getName());
				// 分页去数据库查询数据 这里可以去数据库查询每一页的数据
				List<T> data = getExportService(serviceName).handleData(param);
				dawnExportExcelCache.savePageData(param.getPageNum(), data);
				return true;
			} catch (Exception e) {
				log.error("导出异常", e);
			} finally {
				countDownLatch.countDown();
			}
			return false;
		}
	}

}
