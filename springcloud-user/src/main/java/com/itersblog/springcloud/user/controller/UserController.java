package com.itersblog.springcloud.user.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private Environment env;
	
	@GetMapping("{id}")
	public Map<String,Object> getUser(@PathVariable Long id) {
		long start=System.currentTimeMillis();
		logger.info("request={id="+id+"}");
//		try {
//			Thread.sleep(1500);
//		} catch (InterruptedException e) {
//			logger.error(e.getMessage(),e);
//		}
		Map<String,Object> user=new LinkedHashMap<String,Object>();
		user.put("id", id);
		user.put("name", env.getProperty("name","default"));
		Map<String,Object> result=new LinkedHashMap<String,Object>();
		result.put("code", 0);
		result.put("message", "OK");
		result.put("user", user);
		logger.info("response="+result+",time="+(System.currentTimeMillis()-start)+"ms");
		return result;
	}
}
