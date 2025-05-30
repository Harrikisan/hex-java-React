package com.casestudy.AmazeCare;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.casestudy.AmazeCare.Enum.Role;

@Configuration
public class SecurityConfig {

	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/api/patient/insert").permitAll()
					.requestMatchers("/api/user/signup").permitAll()
					.requestMatchers("/api/doctor/add").permitAll()
					.requestMatchers("/api/doctor/schedule/add").hasAnyAuthority(Role.DOCTOR.toString())
					.requestMatchers("/api/doctor/schedule/get-by-doctor").
					hasAnyAuthority(Role.DOCTOR.toString(),Role.PATIENT.toString(),Role.NURSE.toString())
					.requestMatchers("/api/doctor/appointment/add/{patient_id}/{doctor_id}/{schedule_id}").permitAll()
					.requestMatchers("/api/doctor/schedule/get-available-slots-by-doctor_id").permitAll()
					.requestMatchers("api/test/schedule/add").permitAll()
					.requestMatchers("/api/lab/add").permitAll()
					.requestMatchers("/api/lab/test/add/*").permitAll()
					.anyRequest().authenticated()  
			)
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
