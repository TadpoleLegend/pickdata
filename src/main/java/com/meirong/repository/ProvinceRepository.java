package com.meirong.repository;


import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.meirong.entity.Province;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public interface ProvinceRepository extends JpaRepository<Province, Integer> , JpaSpecificationExecutor<Province>{
	List<Province> findByName(String name);
	
	ProvinceRepository getProvinceRepository();
}
