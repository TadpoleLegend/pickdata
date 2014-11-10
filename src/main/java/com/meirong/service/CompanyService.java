package com.meirong.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.meirong.entity.Company;
import com.meirong.entity.Problem;
import com.meirong.entity.Step;
import com.meirong.vo.CompanySearchVo;

public interface CompanyService {
	List<Company> findCompany(String name);

	List<Company> findAllCompanies();

	Page<Company> getCompanyInPage(Integer index);

	Page<Company> getCompanyInPage(String companyNameParam, String contactorParam, String starParam, String allStarCheckboxParam, String distinctParam, Integer pageNumber);

	Page<Company> getCompanyInPage(CompanySearchVo companySearchVo);

	Problem saveProblem(Problem problem);

	Step saveStep(Step step);
}
