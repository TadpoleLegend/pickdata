package com.meirong.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.meirong.entity.Company;
import com.meirong.entity.Problem;
import com.meirong.entity.Step;
import com.meirong.repository.CompanyRepository;
import com.meirong.repository.ProblemRepository;
import com.meirong.repository.StepRepository;
import com.meirong.service.CompanyService;
import com.meirong.vo.CompanySearchVo;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private StepRepository stepRepository;

	public List<Company> findCompany(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> findAllCompanies() {
		return companyRepository.findAll();
	}

	public Page<Company> getCompanyInPage(Integer index) {

		return companyRepository.findAll(new PageRequest(index, 10));
	}

	public Problem saveProblem(Problem problem) {

		return problemRepository.save(problem);
	}

	public Step saveStep(Step step) {

		return stepRepository.save(step);
	}

	public Page<Company> getCompanyInPage(String companyNameParam, String contactorParam, String starParam, String allStarCheckboxParam, String distinctParam, Integer pageNumber) {
		Page<Company> companyPage = companyRepository.findAll(generateSpecification(companyNameParam, contactorParam, starParam, allStarCheckboxParam, distinctParam), new PageRequest(pageNumber, 5));
		
		return companyPage;
	}
	
	public Page<Company> getCompanyInPage(final CompanySearchVo companySearchVo) {
		
		 Page<Company> companyPage = companyRepository.findAll(generateSpecification(companySearchVo), new PageRequest(Integer.valueOf(companySearchVo.getPageNumber()), 5));
		 
		 return companyPage;
	}
	
	private Specification<Company> generateSpecification(final String companyNameParam, final String contactorParam, final String starParam, final String allStarCheckboxParam, final String distinctParam) {
		
		return new Specification<Company>() {

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				
				if (StringUtils.isNotBlank(companyNameParam)) {
					predicate.getExpressions().add(cb.like(root.<String> get("name"), "%" + companyNameParam.trim() + "%"));
				}
				
				if (StringUtils.isNotBlank(contactorParam)) {
					predicate.getExpressions().add(cb.equal(root.<String> get("contactor"), contactorParam.trim()));
				}
				
				if (StringUtils.isNotBlank(distinctParam)) {
					predicate.getExpressions().add(cb.equal(root.<String> get("area"), distinctParam.trim()));
				}
				
				if (StringUtils.isNotBlank(allStarCheckboxParam)) {
					
					//all star true : ignore star value
					if (allStarCheckboxParam.trim().equalsIgnoreCase("false")) {
						predicate.getExpressions().add(cb.equal(root.<String> get("star"), Integer.valueOf(starParam)));
					}
				}
				return predicate;
			}
		};

	}

	private Specification<Company> generateSpecification(final CompanySearchVo companySearchVo) {
		return new Specification<Company>() {

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				
				if (StringUtils.isNotBlank(companySearchVo.getCompanyNameParam())) {
					predicate.getExpressions().add(cb.like(root.<String> get("name"), "%" + companySearchVo.getCompanyNameParam().trim() + "%"));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getContactorParam())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("contactor"), companySearchVo.getContactorParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getDistinctParam())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("area"), companySearchVo.getDistinctParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getAllStarCheckboxParam())) {
					
					//all star true : ignore star value
					if (companySearchVo.getAllStarCheckboxParam().trim().equalsIgnoreCase("false")) {
						predicate.getExpressions().add(cb.equal(root.<String> get("star"), Integer.valueOf(companySearchVo.getAllStarCheckboxParam())));
					}
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getCityId())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("cityId"), companySearchVo.getCityId().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getProvinceId())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("provinceId"), companySearchVo.getProvinceId().trim()));
				}
				return predicate;
			}
		};

	}
	
	
}
