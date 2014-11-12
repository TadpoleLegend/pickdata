package com.meirong.jobs;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.meirong.entity.Company;
import com.meirong.repository.CompanyRepository;
import com.meirong.service.GrabService;
import com.meirong.util.GrapImgUtil;
import com.meirong.util.HtmlParserUtilPlanB;
import com.meirong.util.HttpClientGrabUtil;

public class GrabFeJob extends QuartzJobBean {
	
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("services");

	@Resource(name = "grabService")
	private GrabService grabService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		logger.info("dong yao dong yao");
		
		System.out.println("dongyaodongyao");
		
		// 1. find basic grab url list
		List<String> urls = grabService.findFeCityURLs();
		
		// 2. grab data
		
		for (String url : urls) {
			//find last grab date for the city
			
		//	Long lastGrabTimeMillis =  grabService.findLastGrabMillisForCity(cityId);
			String meirongshiURL = url.endsWith("/") ? url + "meirongshi/" : url + "/meirongshi/";
			
			//page navigation = url + 'pn0...1000'
			
			
			
		}
		
		String testURL = "http://su.58.com/meirongshi/pn0";
		
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);
		
		List<Company> companiesInThisPage = HtmlParserUtilPlanB.getInstance().findPagedCompanyList(htmlForPage);
		
		//<input id="pagenum" value="C29C0040637C187E41C97E412398A6D8A" type="hidden" />
		for (Company company : companiesInThisPage) {
			
			String companyDetailUrl = company.getfEurl();
			String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);
			
		//	String companyName = HtmlParserUtilPlanB.findCompanyName(detailPageHtml);
		//	company.setName(companyName);
			
			String contactor = HtmlParserUtilPlanB.getInstance().findContactorName(detailPageHtml);
			company.setContactor(contactor);
			
			String phoneImgSrc = HtmlParserUtilPlanB.getInstance().findContactorPhoneNumberImgSrc(detailPageHtml);
			company.setPhoneSrc(phoneImgSrc);
			
			String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
			company.setPhoneSrc(imgFileNameAfterGrabed);
			
			companyRepository.save(company);
			
		}
		
		// 3. save to db
		
		// 4. save grab log

	}

}
