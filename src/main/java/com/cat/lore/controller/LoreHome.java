package com.cat.lore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cat.boot.jsonbean.PostBean;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.jsonbean.UserBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.FrameUtils;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.file.model.FileRecord;
import com.cat.lore.model.Lore;
import com.cat.lore.model.LoreMark;

@RestController
@RequestMapping("/lore")
public class LoreHome extends BaseHome<Lore> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2327929120079672740L;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id, HttpServletRequest request) {
		if (!StringUtil.isEmpty(id)) {
			Lore entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			Lore entity = new Lore();
			UserBean u = baseService.getUserInfo(request);
			if (!StringUtil.isListEmpty(u.getPosts())) {
				for (PostBean p : u.getPosts()) {
					// 查询是否拥有该角色
					List<String> xxs = (List<String>) baseService.getList("org_Information_Role", null, false, "role",
							NameQueryUtil.setParams("Information", p.getPiId(), "role", "知识库管理员"));
					if (!StringUtil.isListEmpty(xxs)) {
						entity.setOrg_id(p.getOrganId());
						entity.setOrg_name(p.getOrganName());
						break;
					}
				}
			}
			entity.setSqr_id(u.getId());
			entity.setSqr_name(u.getName());
			return ResultBean.getSucess(entity);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Lore bean) throws Exception {
		boolean isnew = StringUtil.isEmpty(bean.getId());
		List<FileRecord> files = (List<FileRecord>) baseService.getList("FileRecord", "lore", "FileRecord_ByKey",
				NameQueryUtil.setParams("keyValue", isnew ? bean.getTmpId() : bean.getId(), "ebcn",
						"com.cat.lore.model.Lore"));
		if (files.size() > 0) {
			bean.setFileExt(files.get(0).getFileInfo().getFileExt());
		}
		baseService.save(bean);
		if (isnew) {
			minioFileController.replaceTmpId(bean.getTmpId(), bean.getId(), "com.cat.lore.model.Lore");
		}
		return ResultBean.getSucess(bean);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam String id, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Lore entity = findById(id);
		List<LoreMark> hasMark = (List<LoreMark>) baseService.getList("LoreMark", "lore", "LoreMark_ifhas",
				NameQueryUtil.setParams("lore_id", id, "user_id", FrameUtils.getUserInfo(request)));
		json.put("lore", entity);
		json.put("hasmark", hasMark.size() > 0 ? 1 : 0);
		return ResultBean.getSucess(json);
	}

}
