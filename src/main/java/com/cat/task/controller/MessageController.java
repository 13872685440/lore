package com.cat.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.boot.exception.CatException;
import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.CalendarUtil;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.StringUtil;
import com.cat.task.enumable.TaskType;
import com.cat.task.model.Remind;
import com.cat.task.model.TaskExt;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	public BaseService baseService;
	
	// 根据ID 查询任务
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	public String task(@RequestParam String userId) {
		List<TaskExt> tasks = (List<TaskExt>)baseService.getList("TaskExt", "task", 
				"TaskExt_byUserId", NameQueryUtil.setParams("userId",userId));
		Map<String,Object> map = new HashMap<String,Object>();
		int a=0,b=0,c=0;
		if(!StringUtil.isListEmpty(tasks)) {
			for (TaskExt taskExt : tasks) {
				if("1".equals(taskExt.getTime_out_type())){
					a = a + 1;
				} else if("2".equals(taskExt.getTime_out_type())) {
					b = b + 1;
				} else {
					c = c + 1;
				}
			}
			map.put("badge_cs", a);
			map.put("badge_jjcs", b);
			map.put("badge", c);
		} else {
			map.put("badge_cs", 0);
			map.put("badge_jjcs", 0);
			map.put("badge", 0);
		}
		return ResultBean.getSucess(map);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/remind", method = RequestMethod.GET)
	public String remind(@RequestParam String userId) {
		List<Remind> list = (List<Remind>)baseService.getList("Remind", "task", 
				"Remind_byUserId", NameQueryUtil.setParams("userId",userId,"r_state","0"));
		Map<String,Object> map = new HashMap<String,Object>();
		int a=0;
		if(!StringUtil.isListEmpty(list)) {
			for (Remind remind : list) {
				if("任务超时".equals(remind.getR_type())) {
					if(!TaskType.已办.equals(remind.getTask().getTaskType())) {
						a = a + 1;
					}
				} else {
					a = a + 1;
				}
			}
			map.put("badge", a);
		} else {
			map.put("badge", 0);
		}
		return ResultBean.getSucess(map);
	}
	
	public String getDate(String date) {
		try {
			if(CalendarUtil.isToday(date)) {
				return CalendarUtil.getHHmm(CalendarUtil.StringToCalendar(date));	
			}
			if(CalendarUtil.isYesterday(date)) {
				return "昨天";
			} else {
				return CalendarUtil.getMmDd(CalendarUtil.StringToCalendar(date));	
			}
		} catch (CatException e) {
			e.printStackTrace();
		}
		return date;
	}
}
