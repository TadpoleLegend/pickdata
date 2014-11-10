package com.meirong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ls_company_resource")
public class CompanyResource implements Serializable {

	private static final long serialVersionUID = 1553814600145695206L;
	
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	protected String url;
	protected String type;
	@Column(name="grab_count")
	protected Integer grabCount;
	@Column(name="grab_by")
	protected Integer grabBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getGrabCount() {
		return grabCount;
	}
	public void setGrabCount(Integer grabCount) {
		this.grabCount = grabCount;
	}
	public Integer getGrabBy() {
		return grabBy;
	}
	public void setGrabBy(Integer grabBy) {
		this.grabBy = grabBy;
	}
	@Override
	public String toString() {
		return "CompanyResource [id=" + id + ", name=" + name + ", url=" + url
				+ ", type=" + type + ", grabCount=" + grabCount + ", grabBy="
				+ grabBy + "]";
	}
}
