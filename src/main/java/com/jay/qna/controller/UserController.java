package com.jay.qna.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jay.qna.advice.CustomException;
import com.jay.qna.dao.UserDao;
import com.jay.qna.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<UserDao> login(@RequestBody @Valid UserDao userInput) {
		UserDao user = userService.findOne(userInput);
		if(user == null) {
			throw new CustomException("계정이 없습니다.");
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
	}
}
