package com.jay.qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jay.qna.dao.UserDao;
import com.jay.qna.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper mapper;

	public UserDao findOne(UserDao dao) {
		return mapper.findOne(dao);
	}
}
