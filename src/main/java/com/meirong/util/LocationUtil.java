package com.meirong.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.ParserException;

public class LocationUtil {
	private static String str138 = null;
	private static LocationUtil locationUtil;

	private LocationUtil() {
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//str138 = out.toString();

	}

	public static void main(String[] args) {
		getProvinces();
	}

	private static List<Map> getProvinces() {
		List<Map> rsList = new ArrayList<Map>();
		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(str138);
			htmlParser.extractAllNodesThatMatch(new NodeFilter() {
				private static final long serialVersionUID = -9308374232004146L;

				public boolean accept(Node node) {
					if (node instanceof Span) {
						Map<String,Map<String,String>> province = new HashMap<String,Map<String,String>>();
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
																for (Node nd : nodes) {
																	LinkTag linkTag = (LinkTag)nd;
																	String onclick = linkTag.getAttribute("onclick");
																	String rp = onclick.replace("SelectType(", "").replace(")", "");
																	String [] arr = rp.split(",");
																	Map<String,String> city = new HashMap<String,String>();
																	city.put(arr[1].replace("'", "").replace("'", ""), arr[0]);
																	province.put(provinceName, city);
																	System.err.println(provinceName + " -- " + arr[1].replace("'", "").replace("'", "") + "--" + arr[0] );
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
					}
					return false;
				}
			});

		} catch (ParserException e) {

			e.printStackTrace();
		}

		return rsList;
	}

	public static Node findNodeById(String html, final String divId) {

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(
					new NodeFilter() {

						public boolean accept(Node node) {

							if (node instanceof TagNode) {
								TagNode tag = (TagNode) node;

								String id = StringUtils.trimToEmpty(tag
										.getAttribute("id"));

								if (StringUtils.isNotBlank(id)
										&& divId.equals(id)) {
									return true;
								}
							}
							return false;
						}
					}).toNodeArray();

			if (null != nodes && nodes.length > 0) {
				Node foundNode = nodes[0];
				return foundNode;
			}

		} catch (ParserException e) {

			e.printStackTrace();
		}

		return null;
	}

}
