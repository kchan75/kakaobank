package com.kakaobank.out.outlog;

public class OutLog {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OutLog.class);

	public void outLog(String outData) {
		log.info(outData);
	}
}
