package com.cat.task.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.NameQueryUtil;
import com.cat.task.model.TaskExt;

@RestController
@Scope("prototype")
@RequestMapping("/taskext_yb")
public class TaskExtYbQuery extends BaseNqtQuery<TaskExt>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1497379200253776845L;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}
	
	@Override
	public Map<String, String> nameQueryName() {
		return NameQueryUtil.setParam(getClassName(), getXmlPath(), getClassName() + "_yb_mainQuery", null);
	}
}
