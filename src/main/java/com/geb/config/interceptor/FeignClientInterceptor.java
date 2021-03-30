package com.geb.config.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
	
	@Autowired
	private HttpServletRequest request;

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void apply(RequestTemplate template) {
//			String header = request.getHeader(AUTHORIZATION_HEADER);
		String header = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW1pbGVAZW1haWwuY29tIiwiZXhwIjoxNjE3NTEzNTUyfQ.7mLE74IuyRyqJDLy9Z-A7ma3mBcwJNo14mexWlAqiD3og2zONhMHXl68aTVO2G-WvxFde7VGNEV35caxmaD_vg";
			template.header(AUTHORIZATION_HEADER, header);
	}
}
