package com.cat.task.controller;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.task.model.TaskRouter;

@RestController
@RequestMapping("/taskrouter")
public class TaskRouterHome extends BaseHome<TaskRouter>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682638213422040422L;
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			TaskRouter entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			TaskRouter entity = new TaskRouter();
			entity.setClc(baseService.getCode("Task_Task_Router", null, 4).trim());
			entity.setId(entity.getClc());
			return ResultBean.getSucess(entity);
		}
	}

	@RequestMapping(value = "/addlower", method = RequestMethod.GET)
	public String addlower(@RequestParam String scId) {
		TaskRouter entity = new TaskRouter();
		TaskRouter superior = (TaskRouter) baseService.findById(TaskRouter.class, scId);
		entity.setScId(scId);
		entity.setScName(superior.getName());
		entity.setClc(baseService.getCode("Task_Task_Router", scId, 4).trim());
		entity.setId(scId + entity.getClc());

		return ResultBean.getSucess(entity);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TaskRouter entity) throws Exception {
		baseService.save(entity);
		return ResultBean.getSucess(entity);
	}

}
