package com._54year.dawn.excel.task;

import com._54year.dawn.excel.task.impl.DawnTaskSubjectImpl;

public class TestTask {
//	public static void main(String[] args) {
//		DawnTaskObserver<String> testObserver = new DawnTaskObserver<String>() {
//
//			/**
//			 * 准备执行
//			 *
//			 * @param thread 执行线程
//			 * @param param  参数
//			 */
//			@Override
//			public void onStart(Thread thread, Object param) {
//
//			}
//
//			/**
//			 * 执行中
//			 *
//			 * @param thread 执行线程
//			 * @param param  执行参数
//			 */
//			@Override
//			public void onRunning(Thread thread, Object param) {
//
//			}
//
//			/**
//			 * 执行完成
//			 *
//			 * @param thread 执行线程
//			 * @param result 执行结果
//			 * @param param  执行参数
//			 */
//			@Override
//			public void onFinish(Thread thread, String result, Object param) {
//
//			}
//
//			/**
//			 * 执行错误
//			 *
//			 * @param thread 执行线程
//			 * @param e      执行异常
//			 * @param param  执行参数
//			 */
//			@Override
//			public void onError(Thread thread, Exception e, Object param) {
//
//			}
//		};
//		int nnn = 0;
//		for (int i = 0; i < 10; i++) {
//			nnn = i;
//			new Thread(new DawnTaskSubjectImpl<>(testObserver, (n) -> {
//				if ((int) n == 5) {
//					throw new RuntimeException("我执行错误了=====");
//				}
//				return "#这是成功的执行结果#";
//			}, nnn)).start();
//		}
//
//
//	}

}
