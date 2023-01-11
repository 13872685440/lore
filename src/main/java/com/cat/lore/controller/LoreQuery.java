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
import com.cat.lore.model.Lore;

@RestController
@Scope("prototype")
@RequestMapping("/lore")
public class LoreQuery extends BaseNqtQuery<Lore> {

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

	@RequestMapping(value = "/lorelist", method = RequestMethod.GET)
	public String types(HttpServletRequest request, String keyword, int pagesize,String typeid) throws Exception {
		UserBean u = baseService.getUserInfo(request);
		String organ_id = "";
		if (!StringUtil.isListEmpty(u.getPosts())) {
			for (PostBean p : u.getPosts()) {
				organ_id += p.getOrganId() + ",";
			}
		}
		List<Lore> result = (List<Lore>) baseService.getList("Lore", "lore", "Lore_ByOrgId",
				NameQueryUtil.setParams("organ_id", StringUtil.removeLast(organ_id), "keyword", keyword,"typeid",typeid));
		if (result.size() > pagesize) {
			return ResultBean.getSucess(result.subList(0, pagesize-1));
		}
		return ResultBean.getSucess(result);
	}

}
