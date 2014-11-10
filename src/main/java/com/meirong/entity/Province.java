package com.meirong.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ls_province")
public class Province implements Serializable {

	private static final long serialVersionUID = 5299983057729736628L;
	
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	
	@OneToMany(mappedBy="province", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	protected List<City> citys;
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
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + ", citys=" + citys
				+ "]";
	}
	
}
