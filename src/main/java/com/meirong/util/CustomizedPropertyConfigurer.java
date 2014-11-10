package com.meirong.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private static  Map<String,String> ctxProperties;
	
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException{
		super.processProperties(beanFactoryToProcess, props);
		ctxProperties  = new HashMap<String,String>();
		for(Object key : props.keySet()){
			String keyProp = key.toString();
			String valueProp = props.getProperty(keyProp);
			ctxProperties.put(keyProp, valueProp);
		}
	}
	
	public static  String getCtxProperty(String name){
		return ctxProperties.get(name);
	}
	
}
