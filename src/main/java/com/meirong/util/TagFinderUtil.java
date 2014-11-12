package com.meirong.util;

import org.htmlparser.Tag;


public class TagFinderUtil {
	
	public static boolean findCompanyLink(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("a") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("fl");
		
		return find;
	}

	public static boolean findCompanyName(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("div") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("company");
		
		return find;
	}
	
	public static boolean findCompanyTotal(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("em") && "f_c_red".equals(tag.getAttribute("class")) && tag.getAttribute("id").equals("js_totalnum");
		
		return find;
	}
	
	public static boolean findCompanyForGanji(Tag tag) {
			
			boolean find = tag.getTagName().equalsIgnoreCase("dd") && "company".equals(tag.getAttribute("class"));
			
			return find;
		}

}
