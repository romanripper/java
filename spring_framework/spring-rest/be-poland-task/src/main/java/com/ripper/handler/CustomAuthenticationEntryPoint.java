package com.ripper.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ripper.utils.MyUtils;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private ObjectMapper objectMapper = new ObjectMapper();
	
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exc) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.getOutputStream().println(objectMapper.writeValueAsString(MyUtils.prepareBody(exc, HttpServletResponse.SC_UNAUTHORIZED)));

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("My Realm");
        super.afterPropertiesSet();
    }
}