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
import com.cat.boot.util.FrameUtils;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.lore.model.Lore;
import com.cat.lore.model.UserItgRcd;
import com.cat.lore.model.UserLoreItg;
import com.cat.system.model.User;

@RestController
@RequestMapping("/useritgrcd")
public class UserItgRcdHome extends BaseHome<UserItgRcd> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6486477595130206200L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, HttpServletRequest request) {
		if (!StringUtil.isEmpty(id)) {
			UserItgRcd entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			UserItgRcd bean = new UserItgRcd();
			return ResultBean.getSucess(bean);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(UserItgRcd entity) throws Exception {
		baseService.save(entity);
		return ResultBean.getSucess(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/jf", method = RequestMethod.POST)
	public String jf(HttpServletRequest request, String lore_id) throws Exception {
		if (StringUtil.isEmpty(lore_id)) {
			return ResultBean.getSucess(null);
		} else {
			String userId = FrameUtils.getUserInfo(request);
			List<UserItgRcd> list = (List<UserItgRcd>) baseService.getList("UserItgRcd", "lore", "UserItgRcd_has",
					NameQueryUtil.setParams("lore_id", lore_id, "user_id", userId));
			if (list.size() == 0) {
				Lore lore = (Lore) baseService.findById(Lore.class, lore_id);
				UserItgRcd entity = new UserItgRcd();
				entity.setIntegral(lore.getScore());
				entity.setUser_id(userId);
				entity.setLore_id(lore_id);
				entity.setLore_name(lore.getTitle());
				baseService.save(entity);
				List<UserLoreItg> jfs = (List<UserLoreItg>) baseService.getList("UserLoreItg", "lore",
						"UserLoreItg_ByUserId", NameQueryUtil.setParams("user_id", userId));
				if (jfs.size() != 0) {
					UserLoreItg bean = jfs.get(0);
					bean.setIntegral(bean.getIntegral() + lore.getScore());
					baseService.save(bean);
				} else {
					UserBean u = baseService.getUserInfo(request);
					String organ_id = "";
					if (!StringUtil.isListEmpty(u.getPosts())) {
						for (PostBean p : u.getPosts()) {
							organ_id += p.getOrganId() + ",";
						}
					}
					UserLoreItg bean = new UserLoreItg();
					bean.setUser_id(userId);
					bean.setUser_name(u.getName());
					bean.setIntegral(lore.getScore());
					bean.setOrgan_id(organ_id);
					baseService.save(bean);
				}

			}
			return ResultBean.getSucess(null);
		}
	}

}
