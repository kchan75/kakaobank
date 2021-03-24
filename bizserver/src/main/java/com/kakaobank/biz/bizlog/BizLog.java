package com.kakaobank.biz.bizlog;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class BizLog {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BizLog.class);	
	private JSONParser parser = new JSONParser();
	
//	public void inLog(Map<String, JSONObject> obj) {
//		log.info(JSONObject.toJSONString(obj));		
//	}
	
	public void bizLog(String data) {
		log.info(data);		
	}
	
}
