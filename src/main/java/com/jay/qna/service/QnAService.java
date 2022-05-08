package com.jay.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jay.qna.dao.AnswerDao;
import com.jay.qna.dao.QnADao;
import com.jay.qna.mapper.QnAMapper;

@Service
public class QnAService {
	
	@Autowired
	private QnAMapper mapper;
	
	public void addQnA(QnADao qnaDao) {
		mapper.addQnA(qnaDao);
	}
	
	public List<QnADao> findAll(QnADao qnaDao) {
		return mapper.findAll(qnaDao);
	}
	
	public QnADao findOne(QnADao qnaDao) {
		return mapper.findOne(qnaDao);
	}
	
	public void assignQna(QnADao qnaDao) {
		mapper.assignQna(qnaDao);
	}
	
	public void inserAnswer(AnswerDao answerDao) {
		mapper.insertAnswer(answerDao);
	}
	
	public void updateStatusComplete(QnADao qnaDao) {
		mapper.updateStatusComplete(qnaDao);
	}
	
	public QnADao findOneWithPassword(QnADao qnaDao) {
		return mapper.findOneWithPassword(qnaDao);
	}
}
