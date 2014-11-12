package com.meirong.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.util.ParserException;

import com.meirong.entity.Company;

public abstract class BaseHtmlParseUtil {
	
	public static Div findFirstOneWithClassName(String html, final String className) {
		Node[] nodes = null;
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);
			
			nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {
				
				public boolean accept(Node node) {
					if (node instanceof Div && StringUtils.isNotBlank(((Div) node).getAttribute("class")) && ((Div) node).getAttribute("class").equalsIgnoreCase(className)) {
						return true;
					}
					return false;
				}
			}).toNodeArray();
			
		} catch (ParserException e) {
			return null;
		}
		
		if (nodes != null && nodes.length > 0) {
			return (Div) nodes[0];
		}
		
		return null;
	}
	
	public static Node findNodeById(String html, final String divId) {

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				public boolean accept(Node node) {

					if (node instanceof TagNode) {
						TagNode tag = (TagNode) node;

						String id = StringUtils.trimToEmpty(tag.getAttribute("id"));

						if (StringUtils.isNotBlank(id) && divId.equals(id)) {
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
	
	public abstract List<Company> findPagedCompanyList(String wholeCityPageHTML);
	public abstract String findCompanyAddress(String wholeCityPageHTML);
	public abstract String findContactorPhoneNumberImgSrc(String detailPageHtml);
	public abstract String findContactorName(String detailPageHtml);
	public abstract String findCompanyEmployeeCount(String detailPageHtml);
	public abstract String findCompanyDescription(String html);
}
