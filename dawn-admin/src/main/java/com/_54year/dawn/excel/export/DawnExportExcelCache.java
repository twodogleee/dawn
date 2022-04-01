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
	private final AtomicInteger nowPage;
	private final LinkedBlockingQueue<List<T>> dataCache;

	public DawnExportExcelCache(int cacheSize) {
		this.nowPage = new AtomicInteger(1);
		this.dataCache = new LinkedBlockingQueue<>(cacheSize);
	}

	public boolean savePageData(int page, List<T> pageData) throws InterruptedException {
		while (nowPage.get() != page) {
			System.out.println(Thread.currentThread().getName() + "开始自旋,当前线程页码" + page + ",当前缓存页码" + nowPage.get());
		}
		dataCache.put(pageData);
		nowPage.set(page + 1);
		return true;
	}

	public List<T> getPageData() throws InterruptedException {
		return dataCache.take();
	}

}
