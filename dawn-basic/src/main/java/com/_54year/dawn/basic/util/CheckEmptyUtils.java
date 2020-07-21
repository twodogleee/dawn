package com._54year.dawn.basic.util;

import java.util.List;
import java.util.Map;

/**
 * 判断是否为空 工具
 *
 * @author Andersen
 */
public class CheckEmptyUtils {
	/**
	 * 判断字符串是否为空
	 *
	 * @param str 字符串
	 * @return true 为空 false 不为空
	 */
	public static boolean stringIsEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * 判断字符串是否不为空
	 *
	 * @param strings 字符串
	 * @return true不为空 false为空
	 */
	public static boolean stringIsNotEmpty(String... strings) {
		for (String string : strings) {
			//有一个参数为空 则为空
			if (stringIsEmpty(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断所有字符串是否都为空
	 *
	 * @param strings 字符串
	 * @return true为空 false不为空
	 */
	public static boolean stringIsAllEmpty(String... strings) {
		for (String s : strings) {
			//有一个参数不为空 则不为空
			if (!stringIsEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断集合是否为空
	 *
	 * @param list 集合
	 * @return true为空 false不为空
	 */
	public static boolean listIsEmpty(List list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 判断map是否为空
	 *
	 * @param map map
	 * @return true为空 false不为空
	 */
	public static boolean mapIsEmpty(Map map) {
		return map == null || map.isEmpty();
	}
}
