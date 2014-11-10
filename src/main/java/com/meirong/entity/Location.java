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
@Table(name="ls_location")
public class Location implements Serializable {

	private static final long serialVersionUID = -168881302111176551L;
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "locations", fetch = FetchType.LAZY)
	protected List<User> users;
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
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
	
}
