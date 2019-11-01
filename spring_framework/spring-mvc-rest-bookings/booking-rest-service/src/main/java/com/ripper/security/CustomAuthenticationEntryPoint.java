package com.ripper.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ripper.response.ControllerErrorResponse;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exc)
			throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().println(objectMapper.writeValueAsString(new ControllerErrorResponse(
				HttpServletResponse.SC_UNAUTHORIZED, exc.getMessage(), System.currentTimeMillis())));

	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("My Realm");
		super.afterPropertiesSet();
	}
}