package com.meirong.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.ParserException;

import com.meirong.entity.Province;

public class LocationUtil {
	private static String str138 = null;
	private static LocationUtil locationUtil;
	private static String place_138_str = "http://s.138job.com/hire/1?keyword=&workadd={0}&keywordtype=1&position=0";
	private LocationUtil() {}

	public static LocationUtil getInstance() {
		if (locationUtil == null) {
			return new LocationUtil();
		} else {
			return locationUtil;
		}
	}

	static {
		InputStream is = LocationUtil.class.getClassLoader().getResourceAsStream("138_city.jsp");
		StringBuffer out = new StringBuffer();
		try {
			byte[] b = new byte[4096];
			for (int n; (n = is.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			str138 = new String(out.toString().getBytes("UTF-8"));  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getProvinces();
//		String testURL = "http://www.58.com/changecity.aspx";
//		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);
//		List<Province> list = findCities(htmlForPage);
	}

	private static List<Map> getProvinces() {
		List<Map> rsList = new ArrayList<Map>();
		final Map<String,Map<String,String>> province = new HashMap<String,Map<String,String>>();
		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(str138);
			htmlParser.extractAllNodesThatMatch(new NodeFilter() {
				private static final long serialVersionUID = -9308374232004146L;

				public boolean accept(Node node) {
					if (node instanceof Span) {
						Span span = (Span) node;
						if ("spanSelectbigType".equals(span.getAttribute("class"))) {
							if (span.getChildren() != null) {
								Node[] nodeList = span.getChildren()
										.toNodeArray();
								if (nodeList != null && nodeList.length > 0) {
									Node fNode = nodeList[0];
									if (fNode instanceof LinkTag) {
										LinkTag lt = (LinkTag) fNode;
										if ("siteTop".equals(lt.getAttribute("class"))) {
											String provinceName = lt.getStringText();
										
											Node divNode = nodeList[1];
											if (divNode instanceof Div) {
												if ("major_city".equals(((Div) divNode).getAttribute("class"))) {
													if (((Div) divNode).getChildrenAsNodeArray() != null) {
														Node[] pList = ((Div) divNode).getChildrenAsNodeArray();
														if (pList != null) {
															if (pList[0] instanceof DefinitionList) {
																DefinitionList definitionList = (DefinitionList) pList[0];
																Node[] cNodes = definitionList.getChildrenAsNodeArray();
																DefinitionListBullet df = (DefinitionListBullet) cNodes[1];
																Node[] nodes = df.getChildrenAsNodeArray();
																Map<String,String> city = new HashMap<String,String>();
																for (Node nd : nodes) {
																	LinkTag linkTag = (LinkTag)nd;
																	String onclick = linkTag.getAttribute("onclick");
																	String rp = onclick.replace("SelectType(", "").replace(")", "");
																	String [] arr = rp.split(",");
																	city.put(arr[1].replace("'", "").replace("'", ""), arr[0]);
//																	System.err.println(provinceName + " -- " + arr[1].replace("'", "").replace("'", "") + "--" + arr[0] );
																}
																province.put(provinceName, city);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					return false;
				}
			});

		} catch (ParserException e) {

			e.printStackTrace();
		}
		
		if(!province.isEmpty()){
			for(Entry<String, Map<String, String>>  et:province.entrySet()){
				System.out.println(et.getKey());
				Map<String,String> map = et.getValue();
				for(Entry<String,String> city:map.entrySet()){
					System.err.println(city.getKey()+" -- " + city.getValue());
					System.err.println("url is " +MessageFormat.format(place_138_str, city.getValue()));
				}
			}
		}

		return rsList;
	}
	
	public static List<Province> findCities(final String detailPageHtml) {
		final List<Province> provinces = new ArrayList<Province>();
		final Map<String,Map<String,String>> province = new HashMap<String,Map<String,String>>();
		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = 7680728721047912165L;

				public boolean accept(Node node) {
					
					if (node instanceof DefinitionList ) {
						
						DefinitionList cityList = ((DefinitionList) node);
						if (StringUtils.isNotBlank(cityList.getAttribute("id") ) && cityList.getAttribute("id") .equals("clist")) {
							Node[] nodelist = cityList.getChildren().toNodeArray();
							
							for (int i = 0; i < nodelist.length; i++) {
								if (nodelist[i] instanceof DefinitionListBullet) {
									DefinitionListBullet definitionListBullet = (DefinitionListBullet) nodelist[i];
									
									if (definitionListBullet.getStringText().equals("山东") || 
										definitionListBullet.getStringText().equals("江苏") ||
										definitionListBullet.getStringText().equals("浙江") ||
										definitionListBullet.getStringText().equals("安徽") ||
										definitionListBullet.getStringText().equals("广东") ||
										definitionListBullet.getStringText().equals("福建") ||
										definitionListBullet.getStringText().equals("广西") ||
										definitionListBullet.getStringText().equals("海南") ||
										definitionListBullet.getStringText().equals("河南") ||
										definitionListBullet.getStringText().equals("湖北") ||
										definitionListBullet.getStringText().equals("湖南") ||
										definitionListBullet.getStringText().equals("江西") ||
										definitionListBullet.getStringText().equals("辽宁") ||
										definitionListBullet.getStringText().equals("黑龙江")||
										definitionListBullet.getStringText().equals("吉林") ||
										definitionListBullet.getStringText().equals("四川") ||
										definitionListBullet.getStringText().equals("云南") ||
										definitionListBullet.getStringText().equals("贵州") ||
										definitionListBullet.getStringText().equals("西藏") ||
										definitionListBullet.getStringText().equals("河北") ||
										definitionListBullet.getStringText().equals("山西") ||
										definitionListBullet.getStringText().equals("内蒙古")||
										definitionListBullet.getStringText().equals("陕西") ||
										definitionListBullet.getStringText().equals("新疆") ||
										definitionListBullet.getStringText().equals("甘肃") ||
										definitionListBullet.getStringText().equals("宁夏") ||
										definitionListBullet.getStringText().equals("青海") ||
										definitionListBullet.getStringText().equals("其他")) 
									{
//										Province province = new Province();
//										province.setName(definitionListBullet.getStringText());
										String provinceName = definitionListBullet.getStringText();
										if(nodelist[i + 1] instanceof DefinitionListBullet){
										DefinitionListBullet subCities = (DefinitionListBullet) nodelist[i + 1];
										
										//Node[] cityLinks = subCities.getChildren().toNodeArray();
										Node[] cityLinks = subCities.getChildrenAsNodeArray();
//										List<City> cities = new ArrayList<City>();
										Map<String,String> city = new HashMap<String,String>();
										for (int j = 0; j < cityLinks.length; j++) {
											if (cityLinks[j] instanceof LinkTag) {
												
												LinkTag cityLink = (LinkTag) cityLinks[j];
												
//												City city = new City();
//												city.setName(cityLink.getStringText());
//												city.setUrl(cityLink.getAttribute("href"));
//												city.setProvince(province);
												city.put(cityLink.getStringText(), cityLink.getAttribute("href"));
//												cities.add(city);
											}
											
										}
										
//										province.setCitys(cities);
										
										province.put(provinceName, city);
									}
//										provinces.add(province);
									}
								}
							}
						}
					
					}
					return false;
				}
			});

		} catch (ParserException e) {
			e.printStackTrace();
		}
		if(!province.isEmpty()){
			for(Entry<String, Map<String, String>>  et:province.entrySet()){
				System.out.println(et.getKey());
				Map<String,String> map = et.getValue();
				for(Entry<String,String> city:map.entrySet()){
					System.err.println(city.getKey()+" -- " + city.getValue());
				}
			}
		}
		return provinces;

	}
}
