package com.kakaobank.biz.controller;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaobank.biz.bizlog.BizLog;
import com.kakaobank.biz.service.RandomDataService;

@RestController
public class BizController {

	Random rand = new Random();
	JSONParser parser = new JSONParser();

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BizController.class);
	BizLog BL = new BizLog();
	
	@PostConstruct
	private void init() {
		// TODO Auto-generated method stub
		log.info("BizController START");
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/biz")
	public String start(@RequestBody String data) {

		//Biz 입력전문 로깅
		BL.bizLog(data);
		String service = "";
		
		try {
			//JSONObject로 변형
			JSONObject bizObj = (JSONObject)parser.parse(data);

			//biz영역에서 data 추출 (여기서는 service 명만 추출)
			service = (String)bizObj.get("service");
			log.info("BIZ SERVICE : " + service);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 업무 서비스 호출 대신.. 성공결과를 랜덤으로 하고 데이터로 랜덤으로 만듬
		RandomDataService rds = new RandomDataService();
		JSONObject res;
		
		int ranInt = rand.nextInt(10);
		
		if (ranInt % 2 == 0)
			//결과 성공일경우
			res = rds.getData(true);
		else
			//결과 실패일경우
			res = rds.getData(false);
		
		return res.toJSONString();
	}
}
