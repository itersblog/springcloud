package com.itersblog.springcloud.order.feign.interceptor;

import org.springframework.stereotype.Component;
import com.itersblog.springcloud.order.interceptor.GlobalRequestIdInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class GlobalFeignInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		template.header(GlobalRequestIdInterceptor.GLOBAL_REQUEST_ID_KEY, GlobalRequestIdInterceptor.getGlobalRequestId());
	}

}
