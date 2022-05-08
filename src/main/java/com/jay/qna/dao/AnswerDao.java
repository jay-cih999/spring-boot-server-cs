package com.jay.qna.dao;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerDao {
	public String reply;
	public Long id;
	public Long qna_id;
	public Date createdAt;
	public UserDao user;
}
