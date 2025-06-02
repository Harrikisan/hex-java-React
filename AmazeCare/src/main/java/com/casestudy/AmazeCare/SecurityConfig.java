package com.casestudy.AmazeCare;

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

import com.casestudy.AmazeCare.Enum.Role;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			.authorizeHttpRequests(authorize -> authorize
					//User api
					.requestMatchers("/api/user/signup").permitAll()
					.requestMatchers("/api/user/token").hasAuthority(Role.PATIENT.toString())
					
					//Patient api
					.requestMatchers("/api/patient/insert").permitAll()
					
					//doctor api
					.requestMatchers("/api/doctor/add").hasAnyAuthority(Role.ADMIN.toString())
					.requestMatchers("/api/doctor/schedule/add").hasAnyAuthority(Role.DOCTOR.toString())
					.requestMatchers("/api/doctor/schedule/get-by-doctor").
					 hasAnyAuthority(Role.DOCTOR.toString(),Role.PATIENT.toString(),Role.NURSE.toString())
					.requestMatchers("/api/doctor/appointment/add/*/*/*").permitAll()
					.requestMatchers("/api/doctor/schedule/get-available-slots-by-doctor_id")
					.hasAnyAuthority(Role.PATIENT.toString(),Role.NURSE.toString())
					
					
					//Test lab api
					.requestMatchers("/api/test/schedule/add").permitAll()
					.requestMatchers("/api/lab/add").permitAll()
					.requestMatchers("/api/lab/test/add/*").permitAll()
					
					//Test Appointment api
					.requestMatchers("/api/test/appointment/add/*/*/*/*/*")
					.hasAnyAuthority(Role.PATIENT.toString(),Role.NURSE.toString())
					.requestMatchers("/api/test/appointment/get/*")
					.hasAnyAuthority(Role.PATIENT.toString(),Role.NURSE.toString())
					.requestMatchers("/api/test/appointment/get-all")
					.hasAnyAuthority(Role.PATIENT.toString(),Role.NURSE.toString()) //<-- Only by Admin
					.requestMatchers("api/test/appointment/get-by-patien_id/*")
					.hasAnyAuthority(Role.PATIENT.toString(),Role.NURSE.toString())
					
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
