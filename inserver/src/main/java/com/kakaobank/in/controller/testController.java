package com.kakaobank.in.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@RestController
public class testController {
	
	@CrossOrigin(origins="*") 
	@RequestMapping(value = "/bizTest")
	public void test(HttpServletRequest req) {
		System.out.println("###### TEST ##########");
//		System.out.println(obj.toString());
	}
}
