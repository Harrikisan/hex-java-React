package com.casestudy.AmazeCare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.casestudy.AmazeCare.Enum.Role;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
        		
        		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// User api
				.requestMatchers("/api/user/signup").permitAll().requestMatchers("/api/user/token").permitAll()
				.requestMatchers("/api/user/details").permitAll()

				// Patient api
				.requestMatchers("/api/patient/insert").permitAll()
				.requestMatchers("/api/patient/edit-status/*").permitAll()
				.requestMatchers("/api/patient/get").hasAuthority(Role.PATIENT.toString())
				.requestMatchers("/api/patient/upload/profile-pic").hasAuthority(Role.PATIENT.toString())

				.requestMatchers("/api/patient/get-by-id/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/patient/get-by-name")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString()).requestMatchers("/api/patient/getall")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString())

				// doctor api
				.requestMatchers("/api/doctor/add").permitAll().requestMatchers("/api/doctor/update-info")
				.hasAnyAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/edit-status/*").permitAll()
				.requestMatchers("/api/doctor/getMyPatients").hasAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/profile").hasAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor//upload/profile-pic").hasAuthority(Role.DOCTOR.toString())

				.requestMatchers("/api/doctor/get-all-listing").permitAll()
				.requestMatchers("/api/doctor/get-by-id/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/get-by-name-listing").permitAll()
				.requestMatchers("/api/doctor/get-by-speclization-listing").permitAll()
				.requestMatchers("/api/doctor/get-by-name-search")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())

				// doctor schedule api
				.requestMatchers("/api/doctor/schedule/add").hasAnyAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/schedule/editStatus/*").hasAnyAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/schedule/get-by-doctor/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/schedule/get-by-doctor")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/schedule/get-available-slots-by-doctor_id")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/schedule/get-slots/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/schedule/delete-slot/*").hasAnyAuthority(Role.DOCTOR.toString())

				// doctor appointment api
				.requestMatchers("/api/doctor/appointment/add/*/*/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/appointment/get-dates/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/doctor/appointment/get-by-doctor").hasAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/appointment/get-by-patient").hasAnyAuthority(Role.PATIENT.toString())
				.requestMatchers("/api/doctor/appointment/getTodaysAppointments").hasAnyAuthority(Role.DOCTOR.toString())
				.requestMatchers("/api/doctor/appointment/editStatus/*")
				.hasAnyAuthority(Role.DOCTOR.toString(),Role.PATIENT.toString())

				// Test schedule api
				.requestMatchers("/api/test/schedule/add")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/test/schedule/getAvailableSlots/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				// lab api
				.requestMatchers("/api/lab/add").hasAuthority(Role.ADMIN.toString()).requestMatchers("/api/lab/get-all")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/lab/edit-availability/*").permitAll()
				// test api
				.requestMatchers("/api/lab/test/add/*").hasAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/lab/test/get-all")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				.requestMatchers("/api/lab/test/getByLab/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.DOCTOR.toString(), Role.NURSE.toString())
				// Test Appointment api
				.requestMatchers("/api/test/appointment/add/*/*/*/*/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/test/appointment/get/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/test/appointment/get-all")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString()) // <-- Only by Admin
				.requestMatchers("/api/test/appointment/get-by-patien_id")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/test/appointment/get-dates/*/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.DOCTOR.toString())

				// Nurse api
				.requestMatchers("/api/nurse.add").hasAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/nurse/update").hasAuthority(Role.NURSE.toString())
				.requestMatchers("/api/nurse/editStatus/*").hasAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/nurse/get").hasAuthority(Role.NURSE.toString())

				// Admin api
				.requestMatchers("/api/admin/add").permitAll().requestMatchers("/api/admin/update")
				.hasAuthority(Role.ADMIN.toString()).requestMatchers("/api/admin/get")
				.hasAuthority(Role.ADMIN.toString())

				// Bed api
				.requestMatchers("/api/bed/add/*").hasAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/bed/update/*").hasAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/bed/all")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/available")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/changeAvailability/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())

				// ward api
				.requestMatchers("/api/ward/add").hasAnyAuthority(Role.ADMIN.toString())
				.requestMatchers("/api/ward/all").hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString())
				.requestMatchers("/api/ward/setAvailability/*").hasAnyAuthority(Role.ADMIN.toString())

				// BedBooking api
				.requestMatchers("/api/bed/appointment/book/*/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/get/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/all")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/by-patient/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/by-username")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/by-bed/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/bed/appointment/cancel/*")
				.hasAnyAuthority(Role.PATIENT.toString(), Role.NURSE.toString(), Role.ADMIN.toString())

				// MedicalRecord api
				.requestMatchers("/api/medicalrecord/add/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/all")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/get/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/get/patient")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/get/doctor")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/get/recorddate")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/medicalrecord/get/patient/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				
				// prescription api
				.requestMatchers("/api/prescription/add/batch/*")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())
				.requestMatchers("/api/prescription/getByRecord")
				.hasAnyAuthority(Role.DOCTOR.toString(), Role.NURSE.toString(), Role.ADMIN.toString())

				.anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults()); // <- this activated http basic technique
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() { // <- Bean saves this object in spring's context
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
//	

	

}
