package com.hex.CodingChallenge;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			.authorizeHttpRequests(authorize -> authorize
					
					//User
					.requestMatchers("/api/user/token").authenticated()
					.requestMatchers("/api/user/signup").permitAll()
		
					//doctor
					.requestMatchers("/api/doctor/add").permitAll()
					
					//patient
					.requestMatchers("/api/patient/add").permitAll()
					.requestMatchers("/api/patient/getByDoctor").hasAnyAuthority("DOCTOR")
					
					//medical history
					.requestMatchers("/api/history/add").permitAll()
					.requestMatchers("/api/history/get").permitAll()
					
					//appointment
					.requestMatchers("/api/appointment/add/*/*").permitAll()
					
					.anyRequest().authenticated()  
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) 
		 .httpBasic(Customizer.withDefaults()); //<- this activated http basic technique
		return http.build();
	}
	
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {  //<- Bean saves this object in spring's context
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) 
			throws Exception {
		  return auth.getAuthenticationManager();
	 }
}
