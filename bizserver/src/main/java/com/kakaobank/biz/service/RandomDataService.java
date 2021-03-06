package com.kakaobank.biz.service;

import java.sql.Timestamp;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RandomDataService {
	public JSONObject getData(Boolean isSucc) {
		
		JSONObject resObj = new JSONObject();			
				
		resObj.put("isSucc", isSucc);
		
		if (isSucc) {
			
			Random rd = new Random();
			
			int leftLimit = 48; // numeral '0'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 10;
			String generatedString = rd.ints(leftLimit,rightLimit + 1)
					  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
					  .limit(targetStringLength)
					  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					  .toString();
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			int size = ((Math.abs(rd.nextInt())%3) + 1);
			JSONArray ja = new JSONArray();
			
			for (int i =0 ; i < size; i++ ) {			
				JSONObject tObj = new JSONObject();
				tObj.put("rStr", generatedString);
				tObj.put("rInt", rd.nextInt());
				tObj.put("rLong", rd.nextLong());
				tObj.put("rBoolean", rd.nextBoolean());
//				tObj.put("rTimestamp", timestamp);				
				ja.add(tObj);
			}
			
			resObj.put("bizOutData", ja);			
		}	
		return resObj;
	}
}
