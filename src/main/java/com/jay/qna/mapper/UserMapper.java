package com.jay.qna.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jay.qna.dao.UserDao;

@Mapper
public interface UserMapper {
	UserDao findOne(UserDao dao);
}
