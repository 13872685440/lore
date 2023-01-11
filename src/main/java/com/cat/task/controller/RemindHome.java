package com.cat.task.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseHome;
import com.cat.push.controller.PushController;
import com.cat.task.model.Remind;

@RestController
@RequestMapping("/remind")
public class RemindHome extends BaseHome<Remind>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -882687764073800306L;
	
	@Autowired
	public PushController pushController;

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,Remind entity) {
		if("抄送".equals(entity.getR_type()) || "0".equals(entity.getR_state())) {
			Remind r = (Remind)findById(entity.getId());
			UserBean u = baseService.getUserInfo(request);
			r.setR_state("1");
			baseService.save(r,true);
			
			pushController.pushMessage(u.getId(), 2);
		}
		return ResultBean.getSucess("");
	}
}
