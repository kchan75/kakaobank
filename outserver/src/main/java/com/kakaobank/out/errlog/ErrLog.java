package com.kakaobank.out.errlog;

public class ErrLog {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ErrLog.class);

	public void errLog(String errData) {
		log.info(errData);
	}
}
