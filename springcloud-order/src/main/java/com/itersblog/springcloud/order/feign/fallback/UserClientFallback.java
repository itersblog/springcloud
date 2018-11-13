package com.itersblog.springcloud.order.feign.fallback;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.itersblog.springcloud.order.feign.UserClient;

@Component
public class UserClientFallback implements UserClient {
	
	@Override
	public Map<String, Object> getUser(Long id) {
		//read cache data when fallback
		Map<String,Object> user=new LinkedHashMap<String,Object>();
		user.put("id", id);
		user.put("name", "cacheman");
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("code", 50001);
		result.put("message", "fallback by hystrix for id("+id+") on feign");
		result.put("user", user);
		return result;
	}

}
