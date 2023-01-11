package com.cat.lore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.PostBean;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.lore.model.UserItgRcd;

@RestController
@Scope("prototype")
@RequestMapping("/useritgrcd")
public class UserItgRcdQuery extends BaseNqtQuery<UserItgRcd> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2123198978189904722L;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms, HttpServletRequest request) throws Exception {
		UserBean u = baseService.getUserInfo(request);
		String organ_ids = "";
		if (!StringUtil.isListEmpty(u.getPosts())) {
			for (PostBean p : u.getPosts()) {
				organ_ids += p.getOrganId() + ",";
			}
		}
		parms.getParams().put("organ_id", StringUtil.removeLast(organ_ids));
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

	@Override
	public String query(BaseQueryHelp parms) throws Exception {
 		return null;
	}

}
