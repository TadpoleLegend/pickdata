package com.meirong.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.meirong.entity.City;
import com.meirong.entity.CityURL;
import com.meirong.entity.Company;
import com.meirong.entity.CompanyResource;
import com.meirong.repository.CityRepository;
import com.meirong.repository.CityURLRepository;
import com.meirong.repository.CompanyRepository;
import com.meirong.repository.CompanyResourceRepository;
import com.meirong.service.GrabService;
import com.meirong.util.GrapImgUtil;
import com.meirong.util.HtmlParserUtilPlanB;
import com.meirong.util.HttpClientGrabUtil;
import com.meirong.vo.GrabStatistic;

@Service("grabService")
public class GrabServiceImpl implements GrabService {
	
	@Autowired
	private CityURLRepository cityURLRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public List<String> findFeCityURLs() {
		
		List<String> list = ImmutableList.of("http://su.58.com/", "http://nj.58.com/");
		
		return list;
	}

	/**
	 * grab company name and URL to database 
	 */
	public void grabCompanyResource(String siteURL) {
		
	}

	public void grabAllCompanyResource() {

		List<CityURL> allCityURLs = cityURLRepository.findAll();
		Random random = new Random();
		int i = 0;
		
		for (CityURL cityURL : allCityURLs) {
			
			try {
				String urlInDb = cityURL.getUrl();
				
				String pagedCompanyURL = urlInDb.endsWith("/") ? urlInDb + "meirongshi/pn" : urlInDb + "/meirongshi/pn";
				
				
				
				String pagedCompanyURLWithPageNumber = pagedCompanyURL + i;
				String pagedCompanyHTML = HttpClientGrabUtil.fetchHTMLwithURL(pagedCompanyURLWithPageNumber);
				
				List<Company> companies = HtmlParserUtilPlanB.findPagedCompanyList(pagedCompanyHTML);
				
				for (Company company : companies) {
					
					CompanyResource companyResource = new CompanyResource();
					companyResource.setUrl(company.getfEurl());
					companyResource.setName(company.getName());
					companyResource.setType("58");
					
					companyResourceRepository.save(companyResource);
				}
				
				i++;
				
				if (i == 3) {
					break;
				}
				
				int waitSeconds = random.nextInt(5000);
				Thread.sleep(waitSeconds);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public List<Company> grabCompanyInPage(String indexPageURL) {
		
		String pagedCompanyHTML = HttpClientGrabUtil.fetchHTMLwithURL(indexPageURL);
		
		List<Company> companies = HtmlParserUtilPlanB.findPagedCompanyList(pagedCompanyHTML);
		
		return companies;
	}

	public Company grabCompanyDetail(String detailPageUrl) {
		Company company = new Company();
		
		if (StringUtils.isBlank(detailPageUrl)) {
			throw new IllegalArgumentException("detail page url is empty");
		}
		String detailPageHtml =  HttpClientGrabUtil.fetchHTMLwithURL(detailPageUrl);
		
		return company;
	}

	/**
	 * 只抓10分钟,悠着�?
	 */
	public GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd) {
		long start = System.currentTimeMillis();
		
		GrabStatistic grabStatistic = new GrabStatistic();
		
		// grab util the publish date
		int pageNumber = 0;
		
		int proccessCount = 0;
		int saved = 0;
		int error = 0;
		int duplicate = 0;
		while (true) {
			proccessCount ++;
			String pageURL = url + "meirongshi/pn" + pageNumber;
			
			String html = HttpClientGrabUtil.fetchHTMLwithURL(pageURL);
			
			List<Company> basicCompany = HtmlParserUtilPlanB.findPagedCompanyList(html);
			List<City> citys = cityRepository.findByUrl(url);
			City myCity = citys.get(0);
			for (Company company : basicCompany) {
					if (isDulpicate(company)) {
						duplicate ++;
						continue;
					}
					
					try {
						
						String companyDetailUrl = company.getfEurl();
						String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

						String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
						company.setContactor(contactor);

						String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
						company.setPhoneImgSrc(phoneImgSrc);
						
						String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
						company.setAddress(address);
						
						if (StringUtils.isNotBlank(phoneImgSrc)) {
							String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
							company.setPhoneSrc(imgFileNameAfterGrabed);
						}
						
						String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
						company.setEmailSrc(emailImgSrc);
						
						if (StringUtils.isNotBlank(emailImgSrc)) {
							String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
							company.setEmailSrc(emailImgFileNameAfterGrabed);
						}
						
						company.setCityId(myCity.getId());
						company.setProvinceId(myCity.getProvince().getId());
						
					} catch (Exception e) {
						error ++;
					}
					
					if (ifCompanyDataValueable(company)){
						
						List<Company> companyInDb = companyRepository.findByNameAndFEurl(company.getName(), company.getfEurl());
						
						if (null == companyInDb || companyInDb.isEmpty()) {
							
							try {
								companyRepository.save(company);
								saved ++;
							} catch (Exception e) {
								//
								error ++;
							}
							
						} else {
							duplicate ++;
						}
						
						
						
					} else {
						error ++;
					}
					
					String grabingPublishDate = company.getPublishDate();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-DD");
				try {
					// break condition

					if (StringUtils.isNotBlank(grabingPublishDate)) {

						boolean ifChoosenDateBeforePublishDate = simpleDateFormat.parse("2014-" + grabingPublishDate).before(publishDateEnd);

						if (ifChoosenDateBeforePublishDate) {
							grabStatistic.setSaved(saved);
							grabStatistic.setTotalReaded(proccessCount);
							grabStatistic.setDuplicate(duplicate);

							return grabStatistic;
						}
					}
				} catch (ParseException e) {
						//
					}
			}
			
			pageNumber ++;
			
			long now = System.currentTimeMillis();
			
			long timeLasts = now - start;
			
			if( timeLasts > 1000 * 60 * 10) {
				
				grabStatistic.setSaved(saved);
				grabStatistic.setTotalReaded(proccessCount);
				grabStatistic.setDuplicate(duplicate);

				return grabStatistic;
			}
		}
		
		
	}
	
	private boolean ifCompanyDataValueable(Company company) {
		if (StringUtils.isBlank(company.getName()) || StringUtils.isBlank(company.getContactor()) || StringUtils.isBlank(company.getfEurl()) || StringUtils.isEmpty(company.getPhoneSrc())) 
		{
			return false;
		}
		
		return true;
	}
	
	private boolean isDulpicate(Company company) {
		List<Company> companies = companyRepository.findByNameAndContactorAndArea(company.getName(), company.getContactor(), company.getArea());
		
		if(companies == null || companies.size() == 0) return false;
		
		
		return true;
	}
}
