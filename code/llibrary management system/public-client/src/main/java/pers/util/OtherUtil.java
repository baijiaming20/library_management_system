package pers.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class OtherUtil {
	// 给字符串两边添加单引号
	public static String str(String s) {
		return "'" + s + "'";
	}

	// 判断字符串是否为数字
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	// 去除字符串数组中的元素
	public static String[] removeStr(String[] strings, String str) {
		List<String> list = new ArrayList<>(Arrays.asList(strings));
		list.remove(str);
		return list.toArray(new String[]{});
	}

	// 获取查询结果第一行指定列的内容
	public static Object getColOfRow(List<Map<String, Object>> res, String colName) {
		return res.get(0).get(colName);
	}

	// 获取当前日期字符串
	public static String getDateNow() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}

	// 获取一个月以后的时间
	public static String getDateMonthLater() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}
}
