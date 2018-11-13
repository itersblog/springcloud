package com.itersblog.springcloud.order.feign;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.itersblog.springcloud.order.feign.fallback.UserClientFallback;

@FeignClient(name="${eureka.serviceId.user}",fallback=UserClientFallback.class)//name为被调用者的serviceId，此处name可以使用配置的变量
public interface UserClient {
	
	@GetMapping("/user/{id}")
	public Map<String,Object> getUser(@PathVariable("id") Long id);//@PathVariable必须显式的指定具体的参数名
}
