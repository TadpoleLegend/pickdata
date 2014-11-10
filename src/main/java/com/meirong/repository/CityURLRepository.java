package com.meirong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.meirong.entity.CityURL;

public interface CityURLRepository extends JpaRepository<CityURL, Integer> , JpaSpecificationExecutor<CityURL>{
	
	CityURL findByName(String name);
	CityURL findByUrlAndName(String url, String name);
}
