package com.itersblog.springcloud.order.interceptor;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

public class GlobalRequestIdInterceptor implements HandlerInterceptor {
	public static final String GLOBAL_REQUEST_ID_KEY="GLOBAL_REQUEST_ID";
	private static final HystrixRequestVariableDefault<String> GLOBAL_REQUEST_ID=new HystrixRequestVariableDefault<String>();
	
	public static String getGlobalRequestId(){
		if(HystrixRequestContext.isCurrentThreadInitialized()){
			return GLOBAL_REQUEST_ID.get();
		}else{
			String val=MDC.get(GLOBAL_REQUEST_ID_KEY);
			return val!=null?val.replaceAll("\\[|\\]|\\s", ""):val;
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String globalRequestId=request.getHeader(GLOBAL_REQUEST_ID_KEY);
		if(StringUtils.isBlank(globalRequestId)){
			globalRequestId=UUID.randomUUID().toString().replace("-", "");
		}
		MDC.put(GLOBAL_REQUEST_ID_KEY, "["+globalRequestId+"] ");
		HystrixRequestContext.initializeContext();
		GLOBAL_REQUEST_ID.set(globalRequestId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception {
		MDC.remove(GLOBAL_REQUEST_ID_KEY);
		HystrixRequestContext hystrixRequestContext = HystrixRequestContext.getContextForCurrentThread();
		if(hystrixRequestContext!=null){
			hystrixRequestContext.shutdown();
		}
	}
}
