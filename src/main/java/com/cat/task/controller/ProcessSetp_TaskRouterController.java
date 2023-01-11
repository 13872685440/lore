package com.cat.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.task.jsonbean.ProcessBean;
import com.cat.task.jsonbean.ProcessViewBean;
import com.cat.task.model.ProcessStep;

@RestController
@RequestMapping("/processstep_taskrouter")
public class ProcessSetp_TaskRouterController {
	
	@Autowired
	public BaseService baseService;

	@RequestMapping(value = "/init_step", method = RequestMethod.GET)
	public String init_step(ProcessBean bean) {
		/*ProcessStep s;
		if(StringUtil.isEmpty(bean.getTask_id())) {
			s = (ProcessStep)baseService.getSimple("ProcessStep", null, true
					,NameQueryUtil.setParams("appId",bean.getApp_id()));
		} else {
			s = (ProcessStep)baseService.findById(ProcessStep.class, bean.getTask_id());
		}
		*/
		ProcessViewBean vbean = new ProcessViewBean();
		/*vbean.setTitle(s.getName());
		vbean.setSteps(initSteps(s,bean.getEntity_id()));
		if(StringUtil.isEmpty(bean.getEntity_id())) {
			vbean.setCurrent(0);
		} else {
			// 查询表对应的节点
			Integer xx = (Integer)baseService.getSimple(
					s.getTable_name(), null, true, "step",NameQueryUtil.setParams("id",bean.getEntity_id()));
			vbean.setCurrent(xx);
		}*/
		return ResultBean.getSucess(vbean);
	}
}
