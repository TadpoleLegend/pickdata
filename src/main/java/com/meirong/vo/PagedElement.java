package com.meirong.vo;

import java.util.List;

import org.springframework.data.domain.Page;

public class PagedElement<T> {
	private Page<T> page;
	
	private List<T> elements;
	private long total;
	private int currentIndex;
	private int totalPages;
	
	public PagedElement(Page<T> page) {
		super();
		this.page = page;
		elements = page.getContent();
		total = page.getTotalElements();
		totalPages = page.getTotalPages();
		currentIndex = page.getNumber();
	}

	public List<T> getElements() {
		
		return elements;
	}

	public long getTotal() {
		
		return total;
	}

	public long getCurrentIndex() {
		return currentIndex;
	}

	public int getTotalPages() {
		return totalPages;
	}
	
	
}
