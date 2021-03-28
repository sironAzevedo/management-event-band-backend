package com.geb.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.geb.config.jwt.JWTAuthenticationFilter;
import com.geb.config.jwt.JWTAuthorizationFilter;
import com.geb.config.jwt.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v3/api-docs",
			"/webjars/**" 
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = { 
			"/*/users",			
			"/auth/forgot/**"
	};
	
	private static final String[] USER_MATCHERS_GET = {
			"/*/users",
	}; 

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, USER_MATCHERS_GET).hasAnyRole("ADMIN", "USER")
		.anyRequest().authenticated()
		;
    	
    	http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
