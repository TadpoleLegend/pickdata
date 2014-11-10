package com.meirong.helper.jqgrid;

public class JqParameter {

	private String field;
	private String value;
	private JqSearch comparator;

	public JqParameter(String field, String value) {
		this(field, value, JqSearch.eq);
	}

	public JqParameter(String field, int value) {
		this(field, String.valueOf(value));
	}
	
	public JqParameter(String field, boolean value) {
		this(field, value?"1":"0");
	}
	
	public JqParameter(String field, String value, JqSearch comp) {
		this.field = field;
		this.value = value;
		this.comparator = comp;
	}

	public JqParameter(String field, int value, JqSearch comp) {
		this(field, String.valueOf(value), comp);
	}
	
	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public String getPreparedValue() {
		return this.comparator.prepareValue(this.value);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JqSearch getComparator() {
		return comparator;
	}

	public void setComparator(JqSearch comparator) {
		this.comparator = comparator;
	}

	public String getSql() {
		StringBuffer sql = new StringBuffer(this.field);
		if (this.comparator == null) {
			sql.append(" = ? ");
		} else {
			sql.append(" " + this.comparator.getComparator() + " ? ");
		}
		return sql.toString();
	}

}
