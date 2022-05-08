package com.jay.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jay.qna.dao.AnswerDao;
import com.jay.qna.dao.QnADao;

@Mapper
public interface QnAMapper {
	void addQnA(QnADao qnaDao);
	List<QnADao> findAll(QnADao qnaDao);
	QnADao findOne(QnADao qnaDao);
	void assignQna(QnADao qnaDao);
	void insertAnswer(AnswerDao answerDao);
	void updateStatusComplete(QnADao qnaDao);
	QnADao findOneWithPassword(QnADao qnaDao);
}
