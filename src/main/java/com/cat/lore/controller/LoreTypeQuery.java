package com.cat.lore.controller;

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
import com.cat.lore.model.LoreType;

@RestController
@Scope("prototype")
@RequestMapping("/loretype")
public class LoreTypeQuery extends BaseNqtQuery<LoreType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2123198978189904722L;

	@Override
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String query(BaseQueryHelp parms) throws Exception {
		excuteQuery(parms);
		return ResultBean.getSucess(new QueryResultBean(parms, results));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String id) {
		return super.delete(id);
	}

	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public String types(HttpServletRequest request) throws Exception {
		UserBean u = baseService.getUserInfo(request);
		String organ_id = "xxxx";
		if (!StringUtil.isListEmpty(u.getPosts())) {
			for (PostBean p : u.getPosts()) {
				// 查询是否拥有该角色
				List<String> xxs = (List<String>) baseService.getList("org_Information_Role", null, false, "role",
						NameQueryUtil.setParams("Information", p.getPiId(), "role", "知识库管理员"));
				if (!StringUtil.isListEmpty(xxs)) {
					organ_id = p.getOrganId();
				}
			}
		}
		List<LoreType> types = (List<LoreType>) baseService.getList("LoreType", "lore", "LoreType_ByOrgId",
				NameQueryUtil.setParams("organ_id", organ_id));
		return ResultBean.getSucess(types);
	}

}
