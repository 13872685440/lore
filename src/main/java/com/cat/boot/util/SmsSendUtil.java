package com.cat.boot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

/**
 * 移动短信工具类
 * 
 * @author loushb
 *
 */
public class SmsSendUtil {

	
	//mvn install:install-file -DgroupId=smssdk -DartifactId=masmgc.sdk.sms -Dversion=1.0.3 -Dpackaging=jar -Dfile=D:\apache-maven-3.6.0\lib\masmgc.sdk.sms-1.0.3-SNAPSHOT.jar

	public static void main(String[] args) throws IOException {
		int i = sendMsg("13972545860", "", "SB");
		System.out.println("发送更新短信成功" + i);
	}

	public static int sendMsg(String mobiles, String templateId, String content) throws IOException {
		String apId = "ydgs02";
		String secretKey = "aA10086+";
		String ecName = "宜都市供水总公司"; // 集团名称
		String sign = "gM1fuCEsE"; // 网关签名编码
		String addSerial = ""; // 拓展码 填空
		String url = "http://112.35.1.155:1992/sms/norsubmit";// 请求url
		// String templateId = "xxxx";//模板id
		SmsSendBean sendReq = new SmsSendBean();
		sendReq.setApId(apId);
		sendReq.setEcName(ecName);
		sendReq.setSecretKey(secretKey);
		sendReq.setContent(content);
		sendReq.setMobiles(mobiles);
		sendReq.setAddSerial(addSerial);
		sendReq.setSign(sign);
		sendReq.setTemplateId(templateId);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(sendReq.getEcName()).append(sendReq.getApId()).append(sendReq.getSecretKey())
				.append(sendReq.getMobiles()).append(sendReq.getContent()).append(sendReq.getSign())
				.append(sendReq.getAddSerial());
		sendReq.setMac(MD5.encode(stringBuffer.toString()).toLowerCase());
		String reqText = JSON.toJSONString(sendReq);
		String encode = Base64.encodeBase64String(reqText.getBytes("UTF-8"));
		String resStr = sendPost(url, encode);
		System.out.println("发送短信结果：" + resStr);
		SmsSendResBean sendRes = JSON.parseObject(resStr, SmsSendResBean.class);
		if (sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	private static String sendPost(String url, String param) {
		OutputStreamWriter out = null;

		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(param);
			out.flush();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
