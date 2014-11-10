package com.meirong.repository;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class ProvinceRepositoryImpl implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ProvinceRepository getProvinceRepository() {
		
		return applicationContext.getBean(ProvinceRepository.class);
	}

	public ProvinceRepositoryImpl getProvinceRepositoryImpl() {
		
		return new ProvinceRepositoryImpl();
	}
}
