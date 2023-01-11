package com.cat.system.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.config.MinioTemplate;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseHome;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.file.model.FileInfo;
import com.cat.file.model.FileRecord;
import com.cat.file.util.FileUtils;
import com.cat.system.model.AppVersion;

@RestController
@RequestMapping("/appversion")
public class AppVersionHome extends BaseHome<AppVersion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4784037142755187698L;

	@Autowired
	private MinioTemplate minioTemplate;

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam String id) {
		if (!StringUtil.isEmpty(id)) {
			AppVersion entity = findById(id);
			return ResultBean.getSucess(entity);
		} else {
			AppVersion entity = new AppVersion();
			return ResultBean.getSucess(entity);
		}
	}

	@Transactional
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(AppVersion bean) {
		boolean isnew = StringUtil.isEmpty(bean.getId());
		baseService.save(bean);
		if (isnew) {
			minioFileController.replaceTmpId(bean.getTmpId(), bean.getId(), "com.cat.system.model.AppVersion");
		}
		return ResultBean.getSucess(bean);
	}

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public String version() {
		List<AppVersion> apps = (List<AppVersion>) baseService.getList("AppVersion", "system", "AppVersion_New", null);
		if (apps.size() > 0) {
			return ResultBean.getSucess(apps.get(0));
		}
		return ResultBean.getSucess(null);
	}

	@RequestMapping(value = "/downApk", method = RequestMethod.GET)
	public void downApk(HttpServletResponse response, @RequestParam String id) throws IOException {
 		List<FileRecord> apps = (List<FileRecord>) baseService.getList("FileRecord", "system", "FileRecord_ByKey",
				NameQueryUtil.setParams("keyvalue", id, "ebcn", "com.cat.system.model.AppVersion"));
 		if (apps.size() == 0) {
			return;
		} else {
			FileRecord wd = apps.get(0);
			InputStream in = minioTemplate.getObject(jedisUtil.getMinioPath("uploadpath"),
					wd.getFileInfo().getUrlName());
			FileInfo info = wd.getFileInfo();
			FileUtils.download(response, in, info.getOriginalName(), "1");
		}
	}

}
