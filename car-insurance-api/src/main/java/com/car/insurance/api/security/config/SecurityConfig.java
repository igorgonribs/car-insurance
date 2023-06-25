package com.car.insurance.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.car.insurance.api.enums.RolesEnum;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SecurityProperties properties;
	private final CustomAuthorizationFilter customAuthorizationFilter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
				authenticationManagerBean(), properties);
		customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions().sameOrigin();
		http.authorizeRequests().antMatchers("/h2-console").permitAll();
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/login/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/signup/**").permitAll();
		http.authorizeRequests().antMatchers("/api/v1/insurance/**").hasAuthority(RolesEnum.EMPLOYEE.name());
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
