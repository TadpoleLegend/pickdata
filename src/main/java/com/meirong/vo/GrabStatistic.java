package com.meirong.vo;

import java.io.Serializable;

public class GrabStatistic implements Serializable {
	
	private static final long serialVersionUID = 6542922502029110019L;
	
	private Integer success;
	private Integer duplicate;
	private Integer saved;
	private Integer totalReaded;
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public Integer getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(Integer duplicate) {
		this.duplicate = duplicate;
	}
	public Integer getSaved() {
		return saved;
	}
	public void setSaved(Integer saved) {
		this.saved = saved;
	}
	public Integer getTotalReaded() {
		return totalReaded;
	}
	public void setTotalReaded(Integer totalReaded) {
		this.totalReaded = totalReaded;
	}
}
