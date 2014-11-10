package com.meirong.helper.jqgrid;

public enum JqSearch {
	eq("=", "VALUE"), ne("!=", "VALUE"), cn("LIKE", "%VALUE%"), nc("NOT LIKE", "%VALUE%"), dateSub(">=",
			"DATE_SUB(now(), INTERVAL VALUE DAY)");

	private String comparator;
	private String searchVal;

	private JqSearch(String c, String sVal) {
		this.comparator = c;
		this.searchVal = sVal;
	}

	public static JqSearch findComparator(String input) {
		JqSearch field = null;

		if (input != null) {
			for (JqSearch id : JqSearch.values()) {
				if (id.name().equals(input)) {
					field = id;
				}
			}
		}

		return field;
	}

	public String prepareValue(String input) {
		return this.searchVal != null ? this.searchVal.replaceAll("VALUE", input) : "";
	}

	/**
	 * @return the comparator
	 */
	public String getComparator() {
		return this.comparator;
	}

	/**
	 * @param comparator
	 *            the comparator to set
	 */
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	/**
	 * @return the searchVal
	 */
	public String getSearchVal() {
		return this.searchVal;
	}

	/**
	 * @param searchVal
	 *            the searchVal to set
	 */
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
}
