package com.meirong.ajax.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meirong.common.AjaxPathConstants;
import com.meirong.common.Constants;
import com.meirong.entity.Problem;
import com.meirong.entity.Province;
import com.meirong.repository.ProblemRepository;
import com.meirong.repository.ProvinceRepository;

@Controller
public class BaseAjaxController {
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@RequestMapping(value = Constants.SLASH
			+ AjaxPathConstants.AJAX_FINDALLPROBLEMS)
	public @ResponseBody 
	Problem [] findAllProblems(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		List<Problem> problems = problemRepository.findAll();
		return problems==null?null:(Problem [])problems.toArray();
	}
	
	@RequestMapping(value = Constants.SLASH
			+ AjaxPathConstants.AJAX_FINDALLPROVINCES)
	public @ResponseBody 
	Province [] findAllProvinces() {
		List<Province> provinces = provinceRepository.findAll();
		return provinces==null?null:(Province [])provinces.toArray();
	}
}
