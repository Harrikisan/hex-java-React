package com.hex.CodingChallenge;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hex.CodingChallenge.Exception.ResourceNotFoundException;


@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourse(RuntimeException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg",e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
}
