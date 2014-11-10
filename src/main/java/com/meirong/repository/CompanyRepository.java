package com.meirong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.meirong.entity.Company;


public interface CompanyRepository extends JpaRepository<Company, Integer> , JpaSpecificationExecutor<Company>{
	
	List<Company> findByNameAndFEurl(String name, String fEurl);
	
	List<Company> findByNameAndContactorAndArea(String name, String contactor, String area);
}
