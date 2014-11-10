package com.meirong.helper.jqgrid;

import java.io.Serializable;

public abstract class JqBean implements Serializable {

	/**
	 * Version information
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String oper;
	
	protected String name;
	protected String description;
	
	protected boolean active;

	// Paging
	protected Integer page;
	protected Integer rows;
	
	// Search and Sort Fields
	protected String sord;
	protected String sidx;
	protected String searchString;
	protected String searchField;
	protected String searchOper;
	protected String ediCode;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * @return the id
	 */
	public Integer getIntId() {
		return this.id != null && !this.id.equals("_empty") ? Integer.parseInt(this.id) : null;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the oper
	 */
	public String getOper() {
		return this.oper;
	}

	/**
	 * @param oper
	 *            the oper to set
	 */
	public void setOper(String oper) {
		this.oper = oper;
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
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return this.rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return this.sord;
	}

	/**
	 * @param sord
	 *            the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return this.sidx;
	}

	/**
	 * @param sidx
	 *            the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return this.searchString;
	}

	/**
	 * @param searchString
	 *            the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return this.searchField;
	}

	/**
	 * @param searchField
	 *            the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	/**
	 * @return the searchOper
	 */
	public String getSearchOper() {
		return this.searchOper;
	}

	/**
	 * @param searchOper
	 *            the searchOper to set
	 */
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	/**
	 * @return the ediCode
	 */
	public String getEdiCode() {
		return this.ediCode;
	}

	/**
	 * @param ediCode the ediCode to set
	 */
	public void setEdiCode(String ediCode) {
		this.ediCode = ediCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}