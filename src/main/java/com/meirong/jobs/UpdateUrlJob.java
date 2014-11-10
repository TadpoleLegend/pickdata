package com.meirong.jobs;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.meirong.service.GrabService;

public class UpdateUrlJob extends QuartzJobBean {
	
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("services");

	@Resource(name = "grabService")
	private GrabService grabService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		logger.info("update url start");
		
		// 1. find basic grab url list
		List<String> urls = grabService.findFeCityURLs();
		
		
		logger.info("update url end");

	}

}
