package com.meirong.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TestJob extends QuartzJobBean {

	private Integer count = 0;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		count++;
		System.out.println("" + count + " da da");

	}

}
