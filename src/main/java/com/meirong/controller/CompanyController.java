package com.meirong.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meirong.common.Constants;
import com.meirong.common.ServletPathConstants;
import com.meirong.entity.Company;
import com.meirong.service.CompanyService;
import com.meirong.vo.CompanySearchVo;
import com.meirong.vo.PagedElement;

@Controller
public class CompanyController {
	private static final Gson GSON = new GsonBuilder().setDateFormat("M-dd-yyyy HH:mm:ss").create();

	@Resource(name = "companyService")
	private CompanyService companyService;

	@RequestMapping(value = Constants.SLASH + ServletPathConstants.LOADCOMPANYINPAGE)
	public String loadCompanyInPage(HttpServletRequest request,
			HttpServletResponse response) {

		String pageNumbersString = request.getParameter("pageNumber");
		if (null == pageNumbersString) {
			pageNumbersString = "1";
		}

		String companyNameParam = request.getParameter("seachCompany");
		String contactorParam = request.getParameter("searchContactor");
		String starParam = request.getParameter("starInput");
		String allStarCheckboxParam = request.getParameter("allStar");
		String distinctParam = request.getParameter("searchDistinct");
		String cityId = request.getParameter("cityId");
		String provinceId = request.getParameter("provinceId");

		CompanySearchVo companySearchVo = new CompanySearchVo();
		companySearchVo.setCompanyNameParam(companyNameParam);
		companySearchVo.setContactorParam(contactorParam);
		companySearchVo.setStarParam(starParam);
		companySearchVo.setAllStarCheckboxParam(allStarCheckboxParam);
		companySearchVo.setDistinctParam(distinctParam);
		companySearchVo.setCityId(cityId);
		companySearchVo.setProvinceId(provinceId);
		companySearchVo.setPageNumber(pageNumbersString);

		Page<Company> result = companyService.getCompanyInPage(companySearchVo);

		PagedElement<Company> company = new PagedElement<Company>(result);
		return GSON.toJson(company);
	}
}
