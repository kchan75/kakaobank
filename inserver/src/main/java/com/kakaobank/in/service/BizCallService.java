package com.kakaobank.in.service;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.kakaobank.in.controller.InboundController;

public class BizCallService {
	
	private HttpClient bizClient = null;
	JSONParser parser = new JSONParser();
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BizCallService.class);
	
	public JSONObject callBizController(Object bizData, String bizUrl) throws ParseException {

		String res = "";

		try {

			if (bizClient == null)
				bizClient = HttpClientBuilder.create().build(); // HttpClient 생성

			HttpPost postRequest = new HttpPost(bizUrl); // POST 메소드 URL 새성

			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection", "keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(new StringEntity(bizData.toString())); // json 메시지 입력

			HttpResponse response = bizClient.execute(postRequest);

			// Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] buffer = new byte[1024];
				InputStream is = response.getEntity().getContent();
				BufferedInputStream bis = new BufferedInputStream(is);

				while ((bis.read(buffer)) != -1) {
					res += new String(buffer, "utf-8");
				}
				res = res.trim();
			} else {
				log.error("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (JSONObject)parser.parse(res);
		}
		return (JSONObject)parser.parse(res);
	}

}
