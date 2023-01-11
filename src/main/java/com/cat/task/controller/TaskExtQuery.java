package com.cat.task.controller;

import java.util.Calendar;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.CalendarUtil;
import com.cat.task.model.TaskExt;

@RestController
@Scope("prototype")
@RequestMapping("/taskext")
public class TaskExtQuery extends BaseNqtQuery<TaskExt>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 379071729160602551L;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		if(parms.getParams().get("states") !=null) {
			String state = (String)parms.getParams().get("states");
			if("cs".equals(state)) {
				parms.getParams().put("dqsj", CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance()));
			} else if("jjcs".equals(state)) {
				parms.getParams().put("dqsj", CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance()));
				parms.getParams().put("jjdqsj", CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance(),Calendar.MINUTE,60));
			} else {
				parms.getParams().put("jjdqsj", CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance(),Calendar.MINUTE,60));
			}
		}
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}
}
