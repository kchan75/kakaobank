package com.kakaobank.in.inlog;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class InLog {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InLog.class);	
	private JSONParser parser = new JSONParser();
	
	public void inLog(Map<String, JSONObject> obj) {
		log.info(JSONObject.toJSONString(obj));
	}
	
}
