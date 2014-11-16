package com.meirong.job.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.meirong.entity.Company;
import com.meirong.repository.CompanyRepository;
import com.meirong.repository.CompanyResourceRepository;
import com.meirong.repository.ProblemRepository;
import com.meirong.repository.ProvinceRepository;
import com.meirong.util.HtmlParserUtilFor138;
import com.meirong.util.HttpClientGrabUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabCompany {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	public static void main(String []args){
		try {
			WebClient webClient = login138();
			for(int i=1;i<2;i++){
			System.out.println("************************************************** page "+i+"***************************************begin");
			String testURL = "http://s.138job.com/hire/{0}?keyword=&workadd=1273&keywordtype=1&position=0";
			//String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(MessageFormat.format(testURL, i));
			String htmlForPage = webClient.getPage(MessageFormat.format(testURL, i)).getWebResponse().getContentAsString();
			
			List<Company> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(htmlForPage,webClient);
			System.out.println("************************************************** page "+i+"***************************************end");
			}
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void testGanjiCompanyList() throws Exception{
		//String testURL = "http://sh.ganji.com/meirongshi/";
		login138();
		/*for(int i=1;i<2;i++){
		System.out.println("************************************************** page "+i+"***************************************begin");
		String testURL = "http://s.138job.com/hire/{0}?keyword=&workadd=1273&keywordtype=1&position=0";
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(MessageFormat.format(testURL, i));
		
		List<Company> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(htmlForPage);
		System.out.println("************************************************** page "+i+"***************************************end");
		}*/
//		Assert.assertTrue(!companiesInThisPage.isEmpty());

		
	}
	
	private static WebClient login138(){
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		try {
			String url = "http://cas.138mr.com/login";

			webClient.getOptions().setJavaScriptEnabled(false);
			// Get the first page
			final HtmlPage loginPage = webClient.getPage(url);

			// Get the form that we are dealing with and within that form,
			// find the submit button and the field that we want to change.
			final List<HtmlForm> forms = loginPage.getForms();

			HtmlForm form = null;
			for (HtmlForm singleForm : forms) {
				if (singleForm.getAttribute("id").equals("formlogin")) {
					form = singleForm;
				}
			}

			final HtmlSubmitInput loginButton = form.getInputByName("btnLogin");
			final HtmlTextInput textField = form.getInputByName("txbUserName");
			final HtmlPasswordInput passwordField = form.getInputByName("txbUserPwd");

			// Change the value of the text field
			textField.setValueAttribute("liu_online@163.com");
			passwordField.setValueAttribute("789321");
			
			// click login button
			loginButton.click();
			System.err.println(webClient.getPage(url).getWebResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webClient;
	}
	
	/**
	@Test
	public void testGrabCompanyList() throws Exception{

		String testURL = "http://su.58.com/meirongshi/pn0";

		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);
		
		File file = new File("wholePagedcompanyList.html");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		System.err.println(htmlForPage);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(htmlForPage);
		fileWriter.close();
		
	//	String htmlForPage = Files.toString(file, Charset.defaultCharset());
		
	//	System.out.println(htmlForPage);
		
		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		Assert.assertTrue(!companiesInThisPage.isEmpty());

		for (Company company : companiesInThisPage) {

			System.out.println(company.toString());
		}
	}

	@Test
	public void testGrabCompanyName() throws Exception {

		String htmlForPage = Files.toString(file, Charset.defaultCharset());

		Company testcomCompany = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage).get(0);

		Assert.assertNotNull(testcomCompany.getName());
		Assert.assertNotNull(testcomCompany.getfEurl());
	}

	@Test
	public void testGrabDetailedPage() throws Exception {
		// http://qy.58.com/9880155593991/?PGTID=14042836308430.7793496957798185&ClickID=2
		String testURL = "http://qy.58.com/13571834103302/";
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL, "detailedCompanyPageHtml.txt");

		Assert.assertNotNull(htmlForPage);

	}

	@Test
	public void testGrabContractorInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactor = HtmlParserUtilPlanB.findContactorName(html);

		Assert.assertEquals("", contactor);
	}

	@Test
	public void testGrabEmployeeCountInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactor = HtmlParserUtilPlanB.findCompanyEmployeeCount(html);
		
		System.out.println(contactor);
		Assert.assertEquals("1-49��", contactor);
	}
	
	@Test
	public void testGrabContractorPhoneImgSrcInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorPhone = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(html);

		Assert.assertEquals(contactorPhone, "http://image.58.com/showphone.aspx?t=v55&v=82319568EEFB8401DCC4BEA34EC3736B6");
	}

	@Test
	public void testGrabContractorEmailImgSrcInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorEmail = HtmlParserUtilPlanB.findContactorEmailImgSrc(html);

		Assert.assertEquals(contactorEmail, "http://image.58.com/showphone.aspx?t=v55&v=ADE0982AA4122C1859F727629C15A292B2421493BF67452E");
	}

	@Test
	public void testGrabContractorAddressInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorPhone = HtmlParserUtilPlanB.findCompanyAddress(html);

		Assert.assertEquals(contactorPhone, "");
	}
	
	@Test
	public void testGrabCompanyDescriptionInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String description = HtmlParserUtilPlanB.findCompanyDescription(html);

		//Assert.assertEquals(description, "");
		System.out.println(description);
	}
	@Test
	public void testGrabImg() throws Exception {
		String src = "http://image.58.com/showphone.aspx?t=v55&v=82319568EEFB84016CC4BEA34EC3736B6";
		String imgFileName = GrapImgUtil.grabImgWithSrc(src);
		
		System.out.println(imgFileName);
	}

	@Test
	public void testGrabJob() throws Exception {

		String testURL = "http://su.58.com/meirongshi/pn0";

		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);

		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Company company : companiesInThisPage) {
			
			synchronized (this) {
				try {
					String companyDetailUrl = company.getfEurl();
					String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

					String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
					company.setContactor(contactor);

					String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
					company.setPhoneImgSrc(phoneImgSrc);
					
					String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
					company.setAddress(address);

					String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
					company.setPhoneSrc(imgFileNameAfterGrabed);
					
					String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
					company.setEmailSrc(emailImgSrc);
					
					if (StringUtils.isNotBlank(emailImgSrc)) {
						String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
						company.setEmailSrc(emailImgFileNameAfterGrabed);
					}
					
					System.out.println(company);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				companyRepository.save(company);
				
				//stringBuilder.append(company.toString() + "\n");
			}
		}
		
	//	FileWriter fileWriter = new FileWriter(new File("companies.txt"));
	//	fileWriter.write(stringBuilder.toString());
	//	fileWriter.close();
	}
	
	
	@Test
	public void testGrabJobWithFile() throws Exception {
		
		File file = new File("wholePagedcompanyList.html");
		
		String htmlForPage = Files.toString(file, Charset.defaultCharset());

		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Company company : companiesInThisPage) {
			
			synchronized (this) {
				try {
					String companyDetailUrl = company.getfEurl();
					String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

					String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
					company.setContactor(contactor);

					String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
					company.setPhoneImgSrc(phoneImgSrc);
					
					String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
					company.setAddress(address);

					String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
					company.setPhoneSrc(imgFileNameAfterGrabed);
					
					String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
					company.setEmailSrc(emailImgSrc);
					
					if (StringUtils.isNotBlank(emailImgSrc)) {
						String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
						company.setEmailSrc(emailImgFileNameAfterGrabed);
					}
					
					System.out.println(company);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//companyRepository.save(company);
				
				//stringBuilder.append(company.toString() + "\n");
			}
		}
		
	}

	@Test
	public void testSaveCompany() throws Exception {
		Company testCompany = new Company();
		testCompany.setAddress("������������·");
		testCompany.setContactor("������");
		testCompany.setEmail("sdfasdfasdf");
		testCompany.setPhone("1123345564");
		
		companyRepository.save(testCompany);
	}
	
	@Test
	public void testSaveCompanyURL() throws Exception {
		
		grabService.grabAllCompanyResource();
	}
	@Test
	public void testSaveCompanyURLone() throws Exception {
		
		CompanyResource companyResource = new CompanyResource();
		companyResource.setName("������ŷȪ������������� ");
		companyResource.setUrl("http://qy.58.com/24230403986438/?PGTID=14047374119020.5358016782187727&ClickID=1");
		companyResource.setType("58");
		
		CompanyResource result = companyResourceRepository.save(companyResource);
		
		System.out.println(result.toString());
	}
	
	@Test
	public void testSaveProblem() throws Exception {
		Problem problem = new Problem();
		problem.setName("Jerry̫˧");
		
		Problem result = problemRepository.save(problem);
		
		System.out.println(result.toString());
		
		Company testCompany = new Company();
		testCompany.setAddress("aaa");
		testCompany.setContactor("");
		testCompany.setEmail("jjiang");
		testCompany.setPhone("1123345564");
		List<Problem> problems = ImmutableList.of(result);
		testCompany.setProblems(problems);
		
		Company companyResult = companyRepository.save(testCompany);
		
		System.out.println(companyResult.toString());
		
		
	}
	
	@Test
	public void testGrabCities() throws Exception {
		String urlString = "http://www.58.com/changecity.aspx";
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(urlString);
		
		List<Province> provinces = HtmlParserUtilPlanB.findCities(htmlForPage);
		
		for (Province province : provinces) {
			
			provinceRepository.save(province);
		}
		
		
	}
	
	@Test
	public void testGrabByService() throws Exception {
		String city = "http://su.58.com/";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		grabService.grabCompanyInformationByUrl(city, simpleDateFormat.parse("2014-7-17"));
	}
	
	@Test
	public void testSaveFile() throws Exception {
		
		System.out.println(System.getProperty("user.dir"));
		File file = new File("D:\\workspace\\littleshop\\src\\main\\webapp\\img\\aa.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	@Test
	public void testFindCompanyWithNameContactorArea() {
		List<Company> companies = companyRepository.findByNameAndContactorAndArea("������ŷȪ�������������", "������", "����");
		Assert.assertTrue(companies.size() > 0);
		
		System.out.println(companies);
		
	}
	**/
}
