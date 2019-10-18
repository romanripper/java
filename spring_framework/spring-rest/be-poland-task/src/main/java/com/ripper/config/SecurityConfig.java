package com.ripper.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.ripper.handler.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.ripper")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BasicAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler customAccessDeniedHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select login as username, password, enabled " + "from user where login=?")
				.authoritiesByUsernameQuery("select u.login as username, u_roles.role as authority "
						+ "from user as u, users_roles as u_roles " + "where u.id = u_roles.user_id and u.login=?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users", "/api/rooms").hasAnyRole("EMPLOYEE", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/users", "/api/rooms").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/users", "/api/rooms").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/users", "/api/rooms").hasRole("ADMIN")
				.antMatchers("/api/bookings").hasAnyRole("EMPLOYEE", "ADMIN")
				.antMatchers("/api/bookings/**").hasAnyRole("EMPLOYEE", "ADMIN")
				.and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.httpBasic()
                .realmName("My Realm")
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
				.accessDeniedHandler(customAccessDeniedHandler);

	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
