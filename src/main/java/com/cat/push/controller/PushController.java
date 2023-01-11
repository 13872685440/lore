package com.cat.push.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.boot.catconst.RedisConst;
import com.cat.boot.config.JedisUtil;
import com.cat.boot.jsonbean.PropParamBean;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.StringUtil;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

@Service
public class PushController {
	
	@Autowired
	public JedisUtil jedisUtil;
	
	@Autowired
	public BaseService baseService;
	
	/** 任务推送 */
	public void pushTask(List<String> userIds,String title,String message) {
		push(userIds,1,title,message);
	}
	
	/** 任务推送 */
	public void pushTask(String userId,String title,String message) {
		List<String> userIds = Arrays.asList(userId.split(","));
		push(userIds,1,title,message);
	}
	
	/** 提醒推送 */
	public void pushRemind(List<String> userIds,String title,String message) {
		push(userIds,2,title,message);
	}
	
	/** 提醒推送 */
	public void pushRemind(String userId,String title,String message) {
		List<String> userIds = Arrays.asList(userId.split(","));
		push(userIds,2,title,message);
	}
	
	/** 推送消息 用来刷新用户首页*/
	public void pushMessage(String userId,int type) {
		List<String> userIds = Arrays.asList(userId.split(","));
		pushMessage(userIds,type);
	}
	
	/** 广播 */
	public void pushAll(String title,String message) {
		pushAll(1,title,message);
	}
	
	public void push(List<String> userIds,int type,String title,String message) {
		String APP_KEY = jedisUtil.hget(RedisConst.jiguang_push, "APP_KEY", RedisConst.reload_db);
		String MASTER_SECRET = jedisUtil.hget(RedisConst.jiguang_push, "MASTER_SECRET", RedisConst.reload_db);
		ClientConfig clientConfig = ClientConfig.getInstance();
	    final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
	    List<String> registrationIds = getRegistrationIds(userIds);
	    if(!StringUtil.isListEmpty(registrationIds)) {
		    try {
		    	PushPayload payload = getPushPayload(registrationIds,type,title,message);
				PushResult result = jpushClient.sendPush(payload);
				System.out.println("registrationIds");
				System.out.println(registrationIds);
				System.out.println(result);
				System.out.println("result");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
	    }
	}
	

	public void pushMessage(List<String> userIds,int type) {
		String APP_KEY = jedisUtil.hget(RedisConst.jiguang_push, "APP_KEY", RedisConst.reload_db);
		String MASTER_SECRET = jedisUtil.hget(RedisConst.jiguang_push, "MASTER_SECRET", RedisConst.reload_db);
		ClientConfig clientConfig = ClientConfig.getInstance();
	    final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
	    List<String> registrationIds = getRegistrationIds(userIds);

	    if(!StringUtil.isListEmpty(registrationIds)) {
		    try {
		    	PushPayload payload = getPushPayloadMessage(registrationIds,type);
				PushResult result = jpushClient.sendPush(payload);
				System.out.println("registrationIds");
				System.out.println(registrationIds);
				System.out.println(result);
				System.out.println("result");
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
	    }
	}
	
	 public PushPayload getPushPayload(List<String> userIds,int type,String message,String title) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.registrationId(userIds))
	                .setMessage(Message.content(String.valueOf(type)))
	                .setNotification(Notification.newBuilder().setAlert(message)
	                		.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
	                		.build()).build();           
	    }
	 
	 public PushPayload getPushPayloadMessage(List<String> userIds,int type) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.registrationId(userIds))
	                .setMessage(Message.content(String.valueOf(type))).build();           
	    }
	 
	public void pushAll(int type,String title,String message) {
		String APP_KEY = jedisUtil.hget(RedisConst.jiguang_push, "APP_KEY", RedisConst.reload_db);
		String MASTER_SECRET = jedisUtil.hget(RedisConst.jiguang_push, "MASTER_SECRET", RedisConst.reload_db);
		ClientConfig clientConfig = ClientConfig.getInstance();
		final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
		
		try {
			PushPayload payload = getPushPayload(type,title,message);
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}
		
		 public PushPayload getPushPayload(int type,String message,String title) {
		        return PushPayload.newBuilder()
		                .setPlatform(Platform.all())
		                .setAudience(Audience.all())
		                .setMessage(Message.content(String.valueOf(type)))
		                .setNotification(Notification.newBuilder().setAlert(message)
		                		.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
		                		.build()).build();           
		    }
		 
		 @SuppressWarnings("unchecked")
		public List<String> getRegistrationIds(List<String> userIds) {
			 List<PropParamBean> beans = new ArrayList<PropParamBean>();
			 beans.add(new PropParamBean("in", "and", "id", userIds));
			 beans.add(new PropParamBean("!=", "and", "registration_id", ""));
			 List<String> registrationIds = (List<String>)baseService.getList("sys_users", "", true, 
			    		"registration_id",beans);
			 return registrationIds;
		 }
}
