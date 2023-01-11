package com.cat.quartz.util;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cat.boot.config.JedisUtil;
import com.cat.boot.util.SpringContextUtil;
import com.cat.push.controller.PushController;
import com.cat.quartz.listener.BaseJobService;

public abstract class JobHome implements Job, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5990943217043747120L;

	protected JedisUtil jedisUtil;
	
	protected BaseJobService baseJobService = new BaseJobService();
	
	protected PushController pushController = new PushController();

	public void init() {
		jedisUtil = (JedisUtil)SpringContextUtil.getBean("jedisUtil");
		pushController = (PushController)SpringContextUtil.getBean("pushController");
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		init();
		executeTask();
		destroy();
	}
	
	protected abstract void executeTask();
	
	public void destroy() {
		baseJobService.close();
	}
}
