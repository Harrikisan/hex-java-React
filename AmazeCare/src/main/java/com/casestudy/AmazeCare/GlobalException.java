package com.casestudy.AmazeCare;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.casestudy.AmazeCare.Exception.DoctorNotFoundException;
import com.casestudy.AmazeCare.Exception.DoctorScheduleNotFoundException;
import com.casestudy.AmazeCare.Exception.LabNotFoundException;
import com.casestudy.AmazeCare.Exception.PatientNotFountException;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(exception = PatientNotFountException.class)
	public ResponseEntity<?> PatientNotFound(PatientNotFountException e){
		//Map string in json format
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		//return the err msg
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(exception = DoctorNotFoundException.class)
	public ResponseEntity<?> doctorNotFound(DoctorNotFoundException e){
		//Map string in json format
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		//return the err msg
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(exception = DoctorScheduleNotFoundException.class)
	public ResponseEntity<?> doctorScheduleNotFound(DoctorScheduleNotFoundException e){
		//Map string in json format
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		//return the err msg
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<?> general(Exception e){
		//Map string in json format
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		//return the err msg
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(exception = LabNotFoundException.class)
	public ResponseEntity<?> LabNotFound(LabNotFoundException e){
		//Map string in json format
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		//return the err msg
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
}
