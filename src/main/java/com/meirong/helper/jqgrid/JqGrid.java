/**
 * 
 */
package com.meirong.helper.jqgrid;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meirong.helper.jqgrid.annotations.JqGridIgnore;
import com.meirong.helper.jqgrid.annotations.JqGridInclude;
import com.meirong.helper.jqgrid.annotations.JqGridTagName;

/**
 * @author jtwite
 * 
 * @TODO: Move all of this into a separate jar file so the classes aren't copied
 *        from project to project
 * 
 */
public class JqGrid {
	/** instance of the logger * */
	private static final Log LOG = LogFactory.getLog(JqGrid.class);

	private static final Gson GSON = new GsonBuilder().setDateFormat("M-dd-yyyy HH:mm:ss").create();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String convertToGson(List<?> rows) {
		PagedResult results = new PagedResult();

		results.setPage(1);
		results.setRows(rows);
		results.setTotalRows(rows.size());

		return GSON.toJson(results);
	}

	/**
	 * Using google's GSON to convert the PagedResult to json
	 * 
	 * @param PagedResult
	 *            <Clazz> query results
	 * @return - JSON string
	 */
	public static String convertToGson(PagedResult<?> results) {
		return GSON.toJson(results);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Deprecated
	public static String convertToJson(List<?> rows) {
		PagedResult results = new PagedResult();

		results.setPage(1);
		results.setRows(rows);
		results.setTotalRows(rows.size());

		return JqGrid.convertToJson(results);
	}

	/**
	 * Convert a list of objects into the proper JSON format for the jQuery
	 * plug-in jqGrid
	 * 
	 * @param PagedResult
	 *            <Clazz> query results
	 * @return - JSON String
	 */
	@Deprecated
	public static String convertToJson(PagedResult<?> results) {
		int rowId = 1;
		StringBuffer jBuff = new StringBuffer();

		jBuff.append("{\"page\":\"" + results.getPage() + "\",\"total\":\"" + results.getTotalPages() + "\","
				+ "\"records\":\"" + results.getTotalRows() + "\",\"rows\":[");

		for (Object obj : results.getRows()) {
			try {
				jBuff.append("{\"id\":" + getIdentifier(obj, rowId) + ",");
			} catch (SecurityException e) {
				LOG.error(e);
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			}
			jBuff.append(objectToJson(null, obj) + "}" + (++rowId <= results.getRows().size() ? "," : ""));
		}
		jBuff.append("]}");

		return jBuff.toString();
	}

	@Deprecated
	public static String getIdentifier(Object obj, int rowId) {
		String id = "";
		Field f = null;
		Object idObj = null;
		Class<?> cls = obj.getClass();

		while (id.equals("")) {
			try {
				f = cls.getDeclaredField("id");
				f.setAccessible(true);
				idObj = f.get(obj);

				if (idObj != null) {
					id = idObj.toString();
				} else {
					id = rowId + "";
				}
			} catch (SecurityException e) {
				LOG.error(e);
			} catch (NoSuchFieldException e) {
				cls = cls.getSuperclass();
				if (cls == null) {
					id = rowId + "";
				}
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
			} catch (NullPointerException e) {
				LOG.error(e);
			}
		}

		if (id.equals("-1")) {
			id = rowId + "";
		}

		return id;
	}

	/**
	 * Convert a object into the proper JSON format for the jQuery plug-in
	 * jqGrid
	 * 
	 * @TODO: Replace the class name check with a JqGridInclude annotation on
	 *        the class
	 * @TODO: Replace String with StringBuffer
	 * 
	 * @param obj
	 * @return JSON String
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	private static String objectToJson(Field field, Object obj) {
		String json = "";
		StringBuffer sb = new StringBuffer("");
		List<Field> fields = new ArrayList<Field>();
		Class<?> cls = obj.getClass();
		String className = field != null && field.isAnnotationPresent(JqGridTagName.class) ? field.getAnnotation(
				JqGridTagName.class).value() : obj.getClass().getSimpleName();

		while (cls != null) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}

		for (Field f : fields) {
			try {
				f.setAccessible(true);
				if (!f.isAnnotationPresent(JqGridIgnore.class)) {
					// Only properties with JqGridInclude annotation on their
					// class should be translated
					if (f.get(obj) != null && !Modifier.isStatic(f.getModifiers())
							&& f.get(obj).getClass().isAnnotationPresent(JqGridInclude.class)) {

						sb.append((sb.toString().endsWith(",") || sb.length() == 0 ? "" : ",")
								+ objectToJson(f, f.get(obj)));

					}
					// Arrays should be converted as well
					else if (f.get(obj) != null && !Modifier.isStatic(f.getModifiers()) && f.get(obj) instanceof List) {

						List<Object> list = (List<Object>) f.get(obj);
						sb.append("\"" + className + "." + f.getName() + "\":[");
						for (int l = 0; l < list.size(); l++) {
							sb.append("{" + objectToJson(f, list.get(l)) + "}" + (l < list.size() - 1 ? "," : ""));
						}
						sb.append("]");

					}
					// The object is "Generic", pull the value from it
					else {
						sb.append("\"" + className + "." + f.getName() + "\":" + "\"" + sanitize(f.get(obj) + "")
								+ "\"");
					}
					sb.append(sb.toString().endsWith(",") || sb.length() == 0 ? "" : ",");
				}
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
			}
		}

		if (sb.length() > 0) {
			json = sb.substring(0, sb.length() - 1);
		}

		return json;
	}

	private static String sanitize(String s) {
		return s.replaceAll("\"", "&quot;").replaceAll("\\n|\\r|\\t|\\\\", "");
	}
}
