package com.itersblog.springcloud.api.filter;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class GlobalRequestIdSetFilter extends ZuulFilter {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public static final String GLOBAL_REQUEST_ID_KEY="GLOBAL_REQUEST_ID";
	
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SERVLET_DETECTION_FILTER_ORDER-1;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String globalRequestId=request.getHeader(GLOBAL_REQUEST_ID_KEY);
		if(StringUtils.isBlank(globalRequestId)){
			globalRequestId=UUID.randomUUID().toString().replace("-", "");
		}
		MDC.put(GLOBAL_REQUEST_ID_KEY, "["+globalRequestId+"] ");
		ctx.addZuulRequestHeader(GLOBAL_REQUEST_ID_KEY, globalRequestId);
		logger.info("request for uri("+request.getRequestURI()+")");
		return null;
	}
}
