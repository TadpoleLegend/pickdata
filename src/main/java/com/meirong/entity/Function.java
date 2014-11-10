package com.meirong.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ls_function")
public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7972306768352381380L;

	@Id
	@GeneratedValue
	protected Integer id;

	protected Integer title;

	protected Integer url;

	protected Integer description;

	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "functions", fetch = FetchType.LAZY)
	protected List<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public Integer getUrl() {
		return url;
	}

	public void setUrl(Integer url) {
		this.url = url;
	}

	public Integer getDescription() {
		return description;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
