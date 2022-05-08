package com.jay.qna;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.jay.qna.controller.QnAController;

@SpringBootTest
class QnAControllerTests {
	
	@Autowired
	QnAController qnaController;
	
	private MockMvc mock;
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(qnaController).build();
	}

	@DisplayName("기본으로 입력된 Q&A 목록 조회 테스트 : 성공")
	@Test
	void qnaListTest() throws Exception {
		mock.perform(get("/qna")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].customer_id", is("user1")))
				.andExpect(jsonPath("$.[1].customer_id", is("user2")))
				.andExpect(jsonPath("$.[0].title", is("문의1")))
				.andExpect(jsonPath("$.[1].title", is("문의2")));
	}
	
	@DisplayName("기본으로 입력된 Q&A 상세 조회 테스트 : 성공")
	@Test
	void qnaFindOneTest() throws Exception {
		mock.perform(get("/qna/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.customer_id", is("user1")));
	}
	
	@DisplayName("기본으로 입력된 Q&A 상세 조회(password 입력) 테스트 : 성공")
	@Test
	void qnaFindOnePasswordTest() throws Exception {
		mock.perform(get("/qna/1/pass?password=1234")).andExpect(status().isOk())
				.andExpect(jsonPath("$.customer_id", is("user1")));
	}
	
	@DisplayName("기본으로 입력된 Q&A 상세 조회 테스트 : 실패 : 0 입력 (exception 이용)")
	@Test
	void qnaFindOneFailTest() {
		try {
			mock.perform(get("/qna/0")).andExpect(status().is(400));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals("id 값을 입력하시길 바랍니다.(0제외)", e.getCause().getMessage());
		}
	}	

	@DisplayName("기본으로 입력된 Q&A 상세 조회 테스트 : 실패 : 0 입력 (assertThrows 이용)")
	@Test
	void qnaFindOneFailTest1() throws Exception {
		
		//CustomException 으로 처리 되지 않았음
		assertThrows(NestedServletException.class, () -> {
			mock.perform(get("/qna/0"));
		});
	}
	
	@DisplayName("기본으로 입력된 Q&A 상세 조회 테스트 : 실패 : 없는 ID 입력")
	@Test
	void qnaFindOneFailTest2() {
		try {
			mock.perform(get("/qna/5")).andExpect(status().is(400));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals("해당 id 값으로 조회 되지 않습니다.", e.getCause().getMessage());
		}
	}
	
	@DisplayName("Q&A 입력 테스트 : 성공")
	@Test
	void qnaInsertTest() {
		try {
			mock.perform(post("/qna").contentType(MediaType.APPLICATION_JSON)
					.content("{\"title\": \"1\", \"customer_id\" :\"1\", \"contents\": \"1\", \"name\" : \"1\", \"password\": \"1234\" }"))
					.andExpect(status().isOk());
		} catch (Exception e) {
			
		}
	}
	
	@DisplayName("Q&A 입력 테스트 : 실패 : 필수값 미입력")
	@Test
	void qnaInsertTest1() throws Exception {
		MvcResult result = mock.perform(post("/qna").contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\": \"충전이 안됩니다.\", \"contents\": \"충전이 10분 되다가 안됩니다.\", \"name\" : \"갤럭시 사용자\" }"))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertThat(result.getResolvedException().getMessage()).contains("사용자ID는 필수 입력입니다.");
	}
	
	@DisplayName("Q&A 목록 조회 테스트 : 성공 : 검색어 입력(customer_id)")
	@Test
	void qnaListWithConditionTest() throws Exception {
		mock.perform(get("/qna").param("customer_id" , "r1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].customer_id", is("user1")));
				
	}
	
	@DisplayName("Q&A 목록 조회 테스트 : 검색어 입력(name)")
	@Test
	void qnaListWithConditionTest1() throws Exception {
		mock.perform(get("/qna").param("name", "2번"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].name", is("2번입니다.")));
				
	}
	
	
	@DisplayName("Q&A 할당 테스트 : 성공")
	@Test
	void qnaAssignTest() throws Exception {
		mock.perform(post("/qna/assign/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"managerId\": \"admin\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("assign")));
	}
	
	@DisplayName("Q&A 할당 테스트 : 실패 : 이미 할당된 경우")
	@Test
	void qnaAssignTest1() throws Exception {
		try {
			mock.perform(post("/qna/assign/1").contentType(MediaType.APPLICATION_JSON)
					.content("{\"managerId\": \"admin\"}"))
					.andExpect(status().isBadRequest());					
		} catch(Exception e) {
			assertEquals("이미 할당 되었습니다.", e.getCause().getMessage());
		}
	}
	
	@DisplayName("Q&A 답변 테스트 : 성공")
	@Test
	void qnaAnswerTest() throws Exception {
		mock.perform(post("/qna/answer/3").contentType(MediaType.APPLICATION_JSON)
				.content("{\"reply\": \"서비스센터로 와주십시오.\", \"user\":{ \"id\": \"admin\"} }"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("complete")));
	}
	
	@DisplayName("Q&A 답변 테스트 : 실패 : 이미 완료된 경우")
	@Test
	void qnaAnswerFailTest2() throws Exception {
		try {
			mock.perform(post("/qna/answer/3").contentType(MediaType.APPLICATION_JSON)
					.content("{\"reply\": \"서비스센터로 와주십시오.\", \"user\":{ \"id\": \"admin\"} }"))
					.andExpect(status().isOk());
		} catch(Exception e) {
			assertEquals("이미 완료된 문의 입니다.", e.getCause().getMessage());
		}
	}
	
	@DisplayName("Q&A 답변 테스트 : 실패 : 등록 상태일 경우")
	@Test
	void qnaAnswerFailTest3() throws Exception {
		try {
			mock.perform(post("/qna/answer/2").contentType(MediaType.APPLICATION_JSON)
					.content("{\"reply\": \"서비스센터로 와주십시오.\", \"user\":{ \"id\": \"admin\"} }"))
					.andExpect(status().isOk());
		} catch(Exception e) {
			assertEquals("할당을 먼저 진행 하시길 바랍니다.", e.getCause().getMessage());
		}
	}
	
	@DisplayName("Q&A 답변 테스트 : 실패 : 다른 상담사에게 할당된 경우")
	@Test
	void qnaAnswerFailTest4() throws Exception {
		try {
			mock.perform(post("/qna/answer/1").contentType(MediaType.APPLICATION_JSON)
					.content("{\"reply\": \"서비스센터로 와주십시오.\", \"user\":{ \"id\": \"admin1\"} }"))
					.andExpect(status().isOk());
		} catch(Exception e) {
			assertEquals("이미 다른 상담사에게 할당된 문의 입니다.", e.getCause().getMessage());
		}
	}
}
