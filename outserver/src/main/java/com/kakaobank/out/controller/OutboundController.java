package com.kakaobank.out.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kakaobank.out.errlog.ErrLog;
import com.kakaobank.out.outlog.OutLog;

@RestController
public class OutboundController {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OutboundController.class);

	OutLog OL = new OutLog();
	ErrLog EL = new ErrLog();
	JSONParser parser = new JSONParser();
	SimpleDateFormat  sf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
	
		
	@PostConstruct
	private void init() {
		// TODO Auto-generated method stub
		log.info("OutboundController START");
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/out", method = RequestMethod.POST)
	public String start(@RequestBody String outData) {
		
		String outStr = "";
		
		try {
			JSONObject outObj = (JSONObject)parser.parse(outData);
			
			//전문에 응답으로 셋팅
			((HashMap)outObj.get("common")).put("requestType", "R");
			
			//전문에 응답시간 변경
			Calendar time = Calendar.getInstance();
			String resTime = sf.format(time.getTime());
			((HashMap)outObj.get("common")).put("trxDatetim", resTime);
			
			
			String responseType = (String) ((HashMap)outObj.get("common")).get("responseType");
			if ( responseType.equals("ER") ) {
			
				/*
				 * 에러여부에 따라 에러로깅 추가 
				 * 1. File 로깅 
				 * 2. ELK를 위한 Logstash - 해당부분은 logback의  appender 이용
				 */	
				
				EL.errLog(outObj.toJSONString());
			}
			
			outStr = outObj.toJSONString();
			
			/*
			 * 출력전문 로깅 
			 * 1. File 로깅 
			 * 2. ELK를 위한 Logstash - 해당부분은 logback의  appender 이용
			 */			
			OL.outLog(outStr);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outStr;
	}
}
