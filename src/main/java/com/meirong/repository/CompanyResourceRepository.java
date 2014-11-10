package com.meirong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.meirong.entity.CompanyResource;


public interface CompanyResourceRepository extends JpaRepository<CompanyResource, Integer> , JpaSpecificationExecutor<CompanyResource>{
}
