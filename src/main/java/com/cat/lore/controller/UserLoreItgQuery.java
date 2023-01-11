package com.cat.lore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cat.boot.jsonbean.BaseQueryHelp;
import com.cat.boot.jsonbean.QueryResultBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseNqtQuery;
import com.cat.boot.util.FrameUtils;
import com.cat.boot.util.NameQueryUtil;
import com.cat.lore.model.LoreMark;
import com.cat.lore.model.UserLoreItg;
import com.cat.system.model.User;

@RestController
@Scope("prototype")
@RequestMapping("/userloreitg")
public class UserLoreItgQuery extends BaseNqtQuery<UserLoreItg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2123198978189904722L;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms, HttpServletRequest request) throws Exception {
		parms.getParams().put("user_id", FrameUtils.getUserInfo(request));
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}

	@RequestMapping(value = "/myitg", method = RequestMethod.GET)
	public String myitg(HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		String userId = FrameUtils.getUserInfo(request);
		User user = (User) baseService.findById(User.class, userId);
		json.put("username", user.getName());
		List<UserLoreItg> hasMark = (List<UserLoreItg>) baseService.getList("UserLoreItg", "lore",
				"UserLoreItg_ByUserId", NameQueryUtil.setParams("user_id", FrameUtils.getUserInfo(request)));
		if (hasMark.size() == 0) {
			json.put("jf", 0);
		} else {
			json.put("jf", hasMark.get(0).getIntegral());
		}
		return ResultBean.getSucess(json);

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
