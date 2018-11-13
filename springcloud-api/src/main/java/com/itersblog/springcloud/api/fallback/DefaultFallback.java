package com.itersblog.springcloud.api.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;

@Component
public class DefaultFallback implements FallbackProvider {
	@Resource
	private ObjectMapper objectMapper;
	
	@Override
	public String getRoute() {
		return null;//此处返回路由名称，表明这是哪个路由的回退策略，如果返回null或*，则表示针对所有路由的默认回退策略
	}
	
	@Override
	public ClientHttpResponse fallbackResponse() {
		return fallbackResponse(null);
	}
	
	@Override
	public ClientHttpResponse fallbackResponse(final Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				RequestContext ctx = RequestContext.getCurrentContext();
				Map<String,Object> result=new HashMap<String,Object>();
				result.put("code", 50002);
				result.put("message", "fallback by hystrix for uri("+ctx.getRequest().getRequestURI()+") on zuul");
				return new ByteArrayInputStream(objectMapper.writeValueAsBytes(result));
			}
			
			@Override
			public String getStatusText() throws IOException {
				return "OK";
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return 200;
			}
			
			@Override
			public void close() {
				
			}
		};
	}
}
