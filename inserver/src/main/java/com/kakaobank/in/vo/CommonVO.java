package com.kakaobank.in.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonVO implements Serializable{

	private String guid;
	private String trxDatetim;
	private String requestType;
	private String responseType;
	
}
