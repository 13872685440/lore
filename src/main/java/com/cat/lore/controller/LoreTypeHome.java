package com.cat.lore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.PostBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.lore.model.LoreType;

@RestController
@RequestMapping("/loretype")
public class LoreTypeHome extends BaseHome<LoreType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048419773554189959L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, HttpServletRequest request) {
		if (!StringUtil.isEmpty(id)) {
			LoreType entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			LoreType bean = new LoreType();
			bean.setClc(baseService.getCode("LORE_TYPE", null, 4).trim());
			bean.setId(bean.getClc());
			return ResultBean.getSucess(bean);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(LoreType entity) throws Exception {
		baseService.save(entity);
		return ResultBean.getSucess(entity);
	}

}
