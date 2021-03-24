package com.kakaobank.biz.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BizVO implements Serializable{

	private String bizFirst;
	private String bizLast;
	private int age;
	
}
