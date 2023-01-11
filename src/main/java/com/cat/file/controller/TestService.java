package com.cat.file.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cat.boot.service.BasePrintService;

@Service("testservice")
public class TestService extends BasePrintService {

	@SuppressWarnings("rawtypes")
	@Override
	public Map getDataMap(String keyValue) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("entity.id", "1111");
		dataMap.put("entity.dt.name", "222");
		map.put("dataMap", dataMap);
		// 输入循环
		Map<String, Object> dataList = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 2; i++) {
			Map<String, Object> tmps = new HashMap<String, Object>();
			tmps.put("pram1", 111111);
			tmps.put("pram2", 222222);
			tmps.put("pram3", 333333);
			list.add(tmps);
		}
		dataList.put("list1", list);
		map.put("dataList", dataList);
		return map;
	}

}
