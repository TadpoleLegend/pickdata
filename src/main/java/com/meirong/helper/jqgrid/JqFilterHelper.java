package com.meirong.helper.jqgrid;

public class JqFilterHelper {
	public static String findSortField(JqFilter[] values, String input, String fallback) {
		String field = fallback;
		if (input != null) {
			for (JqFilter id : values) {

				if (id.getJavascriptParameter().equalsIgnoreCase(input)) {

					field = id.getSortField();
				}
			}
		}
		return field;
	}
}
