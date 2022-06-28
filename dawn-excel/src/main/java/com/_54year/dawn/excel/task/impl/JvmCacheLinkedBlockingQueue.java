package com._54year.dawn.excel.task.impl;

import com._54year.dawn.excel.task.DawnCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 内存缓存队列
 *
 * @param <T> 缓存数据类型
 * @author Andersen
 */
@Slf4j
public class JvmCacheLinkedBlockingQueue<T> implements DawnCache<T> {
	/**
	 * 线程公共变量 当前页 记录当前执行页保证写出顺序
	 */
	private final AtomicInteger nowPage;
	/**
	 * 是否执行 当执行错误时停止所有数据添加/取出
	 */
	private final AtomicBoolean executeFlag;
	/**
	 * 队列的大小
	 */
	private final AtomicInteger queueLength;
	/**
	 * 线程公共变量 本地缓存队列
	 */
	private final LinkedBlockingQueue<T> dataCache;

	public JvmCacheLinkedBlockingQueue(int queueLength) {
		this.nowPage = new AtomicInteger(1);
		this.dataCache = new LinkedBlockingQueue<>(queueLength);
		this.queueLength = new AtomicInteger(queueLength);
		this.executeFlag = new AtomicBoolean(true);
	}

	/**
	 * 缓存数据
	 *
	 * @param data 需要缓存的数据
	 * @param page 当前数据的页数
	 * @return 是否成功
	 */
	@Override
	public boolean addData(T data, int page) {
		//如果队列已满 || 当前需要存储的数据页不为顺序页 则先等待
		while (dataCache.size() >= queueLength.get() || page != nowPage.get()) {
			//如果有其他线程执行错误 则结束
			if (!executeFlag.get()) {
				return false;
			}
			log.info("addData()循环等待" + page + "页放入缓存," + "dataCache.size=" + dataCache.size() + ",queueLength=" + queueLength.get() + ",nowPage=" + nowPage.get());
		}
		if (executeFlag.get() && dataCache.offer(data)) {
			nowPage.set(page + 1);
			return true;
		}
		return false;
	}

	/**
	 * 获取缓存数据
	 *
	 * @return 缓存的数据
	 */
	@Override
	public T getData() throws InterruptedException {
		//如果队列没有数据 则循环等待
		while (dataCache.isEmpty()) {
			//如果有其他线程执行错误 则结束
			if (!executeFlag.get()) {
				return null;
			}
			log.info("getData()循环等待");
			Thread.sleep(10);
		}
		if (executeFlag.get()) {
			return dataCache.poll();
		}
		return null;
	}

	/**
	 * 如果执行错误
	 */
	@Override
	public void onError() {
		log.warn("执行错误!");
		//执行标记
		executeFlag.set(false);
		//清除数据
		dataCache.clear();
	}

	/**
	 * 获取执行状态
	 *
	 * @return 执行状态
	 */
	@Override
	public boolean getExecuteFlag() {
		return executeFlag.get();
	}

	/**
	 * 清除队列缓存数据
	 */
	@Override
	public void clearQueue() {
		dataCache.clear();
	}

	/**
	 * 添加数据至队列中
	 * 队列满了 则一直等待
	 *
	 * @param data 数据
	 * @return 是否成功
	 */
	public boolean putQueue(T data) {
		try {
			dataCache.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 添加数据至队列表中
	 * 队列满了 则返回false
	 *
	 * @param data 数据
	 * @return 是否成功
	 */
	public boolean offerQueue(T data) {
		return dataCache.offer(data);
	}

	/**
	 * 获取数据
	 * 当队列为空则阻塞等待
	 *
	 * @return 数据
	 */
	public T takeQueue() {
		try {
			return dataCache.take();
		} catch (InterruptedException e) {
			return null;
		}
	}


	/**
	 * 获取数据
	 * 当队列为空时则返回null
	 *
	 * @return 数据
	 */
	public T pollQueue() {
		return dataCache.poll();
	}


}
