package com.cat.lore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.StringUtil;
import com.cat.lore.model.UserLoreItg;

@RestController
@RequestMapping("/userloreitg")
public class UserLoreItgHome extends BaseHome<UserLoreItg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048419773554189959L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, HttpServletRequest request) {
		if (!StringUtil.isEmpty(id)) {
			UserLoreItg entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			UserLoreItg bean = new UserLoreItg();
			return ResultBean.getSucess(bean);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(UserLoreItg entity) throws Exception {
		baseService.save(entity);
		return ResultBean.getSucess(entity);
	}

}
