package com.itersblog.springcloud.order.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itersblog.springcloud.order.feign.UserClient;

@RestController
@RequestMapping("order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private Environment env;
	@Resource
	private UserClient userClient;
	
	@GetMapping("{id}")
	public Map<String,Object> index(@PathVariable Long id){
		long start=System.currentTimeMillis();
		logger.info("request={id="+id+"}");
		Map<String,Object> result=new LinkedHashMap<String,Object>();
		result.put("code", 0);
		result.put("message", "OK");
		result.put("source", env.getProperty("source","default"));
		result.put("user", userClient.getUser(id).get("user"));
		logger.info("response="+result+",time="+(System.currentTimeMillis()-start)+"ms");
		return result;
	}
}
