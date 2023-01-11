package com.cat.lore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.FrameUtils;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.lore.model.Lore;
import com.cat.lore.model.LoreMark;

@RestController
@RequestMapping("/loremark")
public class LoreMarkHome extends BaseHome<LoreMark> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048419773554189959L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, HttpServletRequest request) {
		if (!StringUtil.isEmpty(id)) {
			LoreMark entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			LoreMark bean = new LoreMark();
			return ResultBean.getSucess(bean);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(LoreMark entity) throws Exception {
		baseService.save(entity);
		return ResultBean.getSucess(entity);
	}

	@RequestMapping(value = "/mymark", method = RequestMethod.GET)
	public String myitg(HttpServletRequest request, String keyword) throws Exception {
		List<Lore> result = (List<Lore>) baseService.getList("LoreMark", "lore", "LoreMark_mylist",
				NameQueryUtil.setParams("keyword", keyword, "user_id", FrameUtils.getUserInfo(request)));
		return ResultBean.getSucess(result);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/mark", method = RequestMethod.POST)
	public String save(String loreId, String hasmark, HttpServletRequest request) throws Exception {
		if (StringUtil.isEmpty(loreId)) {
			return ResultBean.getSucess(null);
		}
		String userId = FrameUtils.getUserInfo(request);
		List<LoreMark> hasMark = (List<LoreMark>) baseService.getList("LoreMark", "lore", "LoreMark_ifhas",
				NameQueryUtil.setParams("lore_id", loreId, "user_id", userId));
		if (hasMark.size() > 0) {
			baseService.update("LoreMark", "lore", "LoreMark_DelById",
					NameQueryUtil.setParams("lore_id", loreId, "user_id", userId));
		} else {
			LoreMark bean = new LoreMark();
			bean.setLore_id(loreId);
			bean.setUser_id(userId);
			baseService.save(bean);
		}
		return ResultBean.getSucess(null);
	}
}
