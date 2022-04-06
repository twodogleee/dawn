package com._54year.dawn.excel.export;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 导出Excel 数据缓存
 *
 * @param <T> 缓存类型
 * @author Andersen
 */
public class DawnExportExcelCache<T> {
	/**
	 * 线程公共变量 当前页 记录当前执行页保证写出顺序
	 */
	private final AtomicInteger nowPage;
	/**
	 * 线程公共变量 本地缓存队列
	 */
	private final LinkedBlockingQueue<List<T>> dataCache;
	/**
	 * Excel任务执行状态
	 */
	private final ExcelTaskStatus excelTaskStatus;


	/**
	 * 创建缓存对象
	 *
	 * @param cacheSize 队列大小
	 */
	public DawnExportExcelCache(int cacheSize, ExcelTaskStatus excelTaskStatus) {
		this.nowPage = new AtomicInteger(1);
		this.dataCache = new LinkedBlockingQueue<>(cacheSize);
		this.excelTaskStatus = excelTaskStatus;
	}


	/**
	 * 加入当前页缓存
	 *
	 * @param page     当前页码
	 * @param pageData 当前页数据
	 * @return 是否成功
	 * @throws InterruptedException
	 */
	public boolean savePageData(int page, List<T> pageData) throws InterruptedException {
		//如果当前页并非执行顺序 则循环等待
		while (nowPage.get() != page) {
			//检测是否可以执行
			if (this.excelTaskStatus.checkTaskError()) {
				return false;
			}
			System.out.println(Thread.currentThread().getName() + "开始自旋,当前线程页码" + page + ",当前缓存页码" + nowPage.get());
		}
		//使用put方法 如果队列已满则等待
		this.dataCache.put(pageData);
		//使记录页数+1
		this.nowPage.set(page + 1);
		return true;
	}

	/**
	 * 从缓存队列中获取数据
	 *
	 * @return 获取到的数据
	 * @throws InterruptedException
	 */
	public List<T> getPageData() throws InterruptedException {
		//检测是否可以执行
		if (this.excelTaskStatus.checkTaskError()) {
			return null;
		}
		//使用take 如果没有数据则等待
		return this.dataCache.take();
	}

}
