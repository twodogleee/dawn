package com._54year.dawn.excel.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 内存缓存队列
 *
 * @param <T> 缓存数据类型
 * @author Andersen
 */
public class JvmCacheLinkedBlockingQueue<T> {
	/**
	 * 线程公共变量 当前页 记录当前执行页保证写出顺序
	 */
	private final AtomicInteger nowPage;
	/**
	 * 线程公共变量 本地缓存队列
	 */
	private final LinkedBlockingQueue<T> dataCache;

	public JvmCacheLinkedBlockingQueue(AtomicInteger nowPage, LinkedBlockingQueue<T> dataCache) {
		this.nowPage = nowPage;
		this.dataCache = dataCache;
	}



}
