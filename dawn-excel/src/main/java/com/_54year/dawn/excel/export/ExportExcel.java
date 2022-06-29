package com._54year.dawn.excel.export;

import com._54year.dawn.core.excetion.DawnBusinessException;
import com._54year.dawn.excel.entity.ExportParam;
import com._54year.dawn.excel.task.DawnCache;
import com._54year.dawn.excel.task.impl.ExcelExportObserver;
import com._54year.dawn.excel.task.impl.ExportDataTaskSubject;
import com._54year.dawn.excel.task.impl.JvmCacheLinkedBlockingQueue;
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
 * 导出Excel实现新
 *
 * @author Andersen
 */
@Service
@Slf4j
public class ExportExcel {
	@Resource(name = "handleDataExecutor")
	private ThreadPoolTaskExecutor handleDataExecutor;

	@Resource(name = "writeDataExecutor")
	private ThreadPoolTaskExecutor writeDataExecutor;

	/**
	 * 使用spring自动注入
	 */
	private final Map<String, AbstractDawnExportExcel> serviceMap;

	public ExportExcel(Map<String, AbstractDawnExportExcel> serviceMap) {
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
	public <T, D> void exportExcelByClass(DawnExportExcelBasicParam param, Class<T> writeClass, String serviceName) {
		DawnCache<List<D>> dawnCache = new JvmCacheLinkedBlockingQueue<>(10);
		try {
			AbstractDawnExportExcel service = serviceMap.get(serviceName);
			//最大条目数
			int total = service.getTotal();
			//页面大小
			int pageSize = param.getPageSize();
			//最大页数
			int maxPageNum = (total + pageSize - 1) / pageSize;
			//循环建立数据处理任务
			for (int i = 1; i <= maxPageNum; i++) {
				ExportParam<DawnExportExcelBasicParam> nowPageParam = new ExportParam<>();
				nowPageParam.setPageNum(i);
				nowPageParam.setPageSize(pageSize);
				//执行数据处理任务
				handleDataExecutor.execute(new ExportDataTaskSubject<>(nowPageParam, service, new ExcelExportObserver<>(), dawnCache));
			}
			String fileName = "D:\\workspace\\test\\dawn\\dawn-excel\\src\\main\\resources\\" + System.currentTimeMillis() + ".xlsx";
			ExcelWriter excelWriter = null;
			long start = System.currentTimeMillis();
			try {
				// 这里 需要指定写用哪个class去写
				excelWriter = EasyExcel.write(fileName, writeClass).build();
				// 这里注意 如果同一个sheet只要创建一次
				WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
				for (int i = 1; i <= maxPageNum; i++) {
					//如果线程出现异常则结束
					if (!dawnCache.getExecuteFlag()) {
						break;
					}
					List<D> data = dawnCache.getData();
					log.info("===============当前写出" + i + "页================" + "最大页:" + maxPageNum);
					excelWriter.write(data, writeSheet);
				}
			} catch (Exception e) {
				dawnCache.onError();
				e.printStackTrace();
			} finally {
				//执行完毕后清除本地缓存
				dawnCache.clearQueue();
				// 千万别忘记finish 会帮忙关闭流
				if (excelWriter != null) {
					excelWriter.finish();
				}
			}
			log.info("=====================写出完毕");
			log.info("===================导出耗时" + (System.currentTimeMillis() - start) + "毫秒");
		} catch (Exception e) {
			dawnCache.onError();
			e.printStackTrace();
		}
	}


}
