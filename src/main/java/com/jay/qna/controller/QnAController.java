package com.jay.qna.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jay.qna.advice.CustomException;
import com.jay.qna.dao.AnswerDao;
import com.jay.qna.dao.QnADao;
import com.jay.qna.service.QnAService;

@RestController
public class QnAController {
	
	@Autowired
	QnAService qnaService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/qna", consumes = "application/json")
	public void addQnA(@RequestBody @Valid QnADao userInput) {
		qnaService.addQnA(userInput);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/qna")
	public List<QnADao> findAll(@RequestParam(value = "status", required=false) String status,
			@RequestParam(value = "customerId", required=false) String customerId,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "title", required=false) String title) {
		QnADao dao = new QnADao();
		
		if(status != null && status != "") {
			String[] arr = status.split(",");
			List<String> statusList = new ArrayList<String>();
			for(int i = 0; i < arr.length; i++) {
				statusList.add(arr[i]);
			}
			dao.setStatusList(statusList);
		} 
		if(customerId != null && customerId != "") {
			dao.setCustomer_id(customerId);
		} if(name != null && name != "") {
			dao.setName(name);
		} if(title != null && title != "") {
			dao.setTitle(title);
		}
		
		return qnaService.findAll(dao);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/qna/{id}")
	public QnADao findOneQna(@PathVariable("id") Long id) {
		QnADao dao = new QnADao();
		
		if(id != null && id != 0) {
			dao.setId(id);
		} else {
			throw new CustomException("id 값을 입력하시길 바랍니다.(0제외)");
		}
		
		QnADao result = qnaService.findOne(dao);
		
		if(result != null) {
			return result;
		} else {
			throw new CustomException("해당 id 값으로 조회 되지 않습니다.");
		}
			
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/qna/assign/{id}", consumes = "application/json" )
	public ResponseEntity<QnADao> assignQna(@RequestBody QnADao userInput, @PathVariable("id") Long id) {
		if(id == null || id == 0) {
			throw new CustomException("Q&A ID를 입력 하시길 바랍니다.");
		}
		
		userInput.setId(id);
		QnADao result = qnaService.findOne(userInput);
		
		if(result == null) {
			throw new CustomException("존재하지 않은 문의 입니다.");
		}
		
		if(!"submit".equals(result.getStatus())) {
			throw new CustomException("이미 할당 되었습니다.");
		}
		
		qnaService.assignQna(userInput);
		QnADao dao = qnaService.findOne(userInput);
		
		return new ResponseEntity<>(dao, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/qna/answer/{id}")
	public ResponseEntity<QnADao> qnaAnswer(@RequestBody AnswerDao userInput, @PathVariable("id") Long id) {
		if(id == null || id == 0) {
			throw new CustomException("Q&A ID를 입력 하시길 바랍니다.");
		}
		
		userInput.setQna_id(id);
		
		QnADao query = new QnADao();
		query.setId(id);
		
		QnADao result = qnaService.findOne(query);
		
		if(result == null) {
			throw new CustomException("존재하지 않은 문의 입니다.");
		}
		
		if("submit".equals(result.getStatus())) {
			throw new CustomException("할당을 먼저 진행 하시길 바랍니다.");
		} else if("complete".equals(result.getStatus())) {
			throw new CustomException("이미 완료된 문의 입니다.");
		}
		
		if(!result.getManagerId().equals((userInput.getUser().getId()))) {
			throw new CustomException("이미 다른 상담사에게 할당된 문의 입니다.");
		}
		
		if(userInput.getReply() == null || "".equals(userInput.getReply())) {
			throw new CustomException("답변을 입력 바랍니다.");
		}
		qnaService.inserAnswer(userInput);
		qnaService.updateStatusComplete(query);
		
		QnADao dao = qnaService.findOne(query);
		
		return new ResponseEntity<>(dao, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/qna/{id}/pass")
	public QnADao findOneQnaWithPassword(@PathVariable("id") Long id,
			@RequestParam String password) {
		QnADao dao = new QnADao();
		
		if(id != null && id != 0) {
			dao.setId(id);
		} else {
			throw new CustomException("id 값을 입력하시길 바랍니다.(0제외)");
		}
		
		if(password != null && !"".equals(password)) {
			dao.setPassword(password);
		} else {
			throw new CustomException("Password 값을 입력하시길 바랍니다.");
		}
		
		QnADao result = qnaService.findOneWithPassword(dao);
		
		if(result != null) {
			return result;
		} else {
			throw new CustomException("비밀 번호가 잘 못 되었습니다.");
		}
	}
}
