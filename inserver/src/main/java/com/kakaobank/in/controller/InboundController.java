package com.kakaobank.in.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kakaobank.in.inlog.InLog;
import com.kakaobank.in.service.BizCallService;
import com.kakaobank.in.service.OutCallService;

@RestController
public class InboundController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InboundController.class);

	@Value("${app.biz}")
	private String biz;

	@Value("${biz.url}")
	private String bizUrl;

	@Value("${out.url}")
	private String outUrl;

	InLog IL = new InLog();

	private BizCallService bizCall = new BizCallService();
	private OutCallService outCall = new OutCallService();
	
	@PostConstruct
	private void init() {
		// TODO Auto-generated method stub
		log.info("InboundController START");
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/kchan", method = RequestMethod.POST)
	public JSONObject start(@RequestBody JSONObject params) {

		/*
		 * 입력전문 로깅 
		 * 1. File 로깅 
		 * 2. ELK를 위한 Logstash - 해당부분은 logback의  appender 이용
		 */
		IL.inLog(params);

		/*
		 * Business Controller 호출 Biz에서는 하나이상의 RDB 호출(FW에서 DataSource 관리) 해당 FW는 조회용으로
		 * BIZ 레이어에서 데이터의 변경이 발생하지 않도록 트랜잭션 관리 필요
		 */
		JSONObject bizParam = null;
		JSONObject outParam = null;
		
		//biz data 영역 추출 
		String bizStr = params.toJSONString((Map) params.get(this.biz));		
		try {
			
			//biz controller에게 전달하기 (with biz data)
			bizParam = bizCall.callBizController(bizStr, this.bizUrl);
			
			Boolean isSucc = (Boolean) bizParam.get("isSucc");
			
			params.put("bizout", bizParam);
			
			//biz 성공여부에 따라 전문의 responseType 항목 셋팅
			if (isSucc) {
//				log.info("성공");
				((HashMap)params.get("common")).put("responseType", "NM");
			}
			else {
//				log.info("실패");
				((HashMap)params.get("common")).put("responseType", "ER");
			}
					
			/*
			 * Outbound Controller 호출
			 * 1. 출력전문 로깅
			 * 2. ELK를 위한 Logstash - 해당부분은 logback의  appender 이용
			 */
			
			outParam = outCall.callOutController(params, this.outUrl);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(outParam.toJSONString());

		return outParam;
	}

}
