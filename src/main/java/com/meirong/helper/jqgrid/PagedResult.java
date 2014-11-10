package com.meirong.helper.jqgrid;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class used to create the proper json formatting for JqGrid
 * 
 * @author jtwite
 *
 * @param <T> - The class to convert to json
 */
public class PagedResult<T> {

	// A list of objects to be viewed in JqGrid
	private List<T> rows;

	// The total number of objects found
	private Integer records;
	
	// The current page displayed in the JqGrid
	private Integer page;
	
	// The number of rows per page in the JqGrid
	private Integer rowsPerPage;
	
	// The total number of pages needed to display all of the results
	private Integer total;

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return this.rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * @return the totalRows
	 */
	public Integer getTotalRows() {
		return this.records;
	}

	/**
	 * @param totalRows
	 *            the totalRows to set
	 */
	public void setTotalRows(Integer totalRows) {
		this.records = totalRows;
		this.total = this.getTotalPages();
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return this.page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
		this.total = this.getTotalPages();
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		int pages = 1;

		if (this.getRowsPerPage() != null && this.getTotalRows() != null) {
			pages = new BigDecimal(this.records).divide(new BigDecimal(this.getRowsPerPage()),
					BigDecimal.ROUND_CEILING).intValue();
		}

		return pages;
	}

	/**
	 * @return the rowsPerPage
	 */
	public Integer getRowsPerPage() {
		return this.rowsPerPage;
	}

	/**
	 * @param rowsPerPage
	 *            the rowsPerPage to set
	 */
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		this.total = this.getTotalPages();
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	};

}
