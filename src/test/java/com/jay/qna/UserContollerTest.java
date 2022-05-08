package com.jay.qna;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.jay.qna.controller.UserController;

@SpringBootTest
public class UserContollerTest {
	@Autowired
	UserController userController;
	
	private MockMvc mock;
	
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(userController).build();
	}

	
	@DisplayName("Login 테스트 : 성공")
	@Test
	void loginTest() throws Exception {
		mock.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content("{\"id\": \"admin\", \"authKey\": \"1234\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("admin")));
				
	}
	
	@DisplayName("Login 테스트 : 실패(필수값 미입력)")
	@Test
	void loginTest1() throws Exception {
		MvcResult result = mock.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content("{\"id\": \"admin\"}"))
				.andExpect(status().isBadRequest())				
				.andReturn();
		
		assertThat(result.getResolvedException().getMessage()).contains("비밀번호는 필수 입력입니다.");				
	}
	
	@DisplayName("Login 테스트 : 실패(없는 계정)")
	@Test
	void qnaFindOneFailTest3() {
		try {
			mock.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content("{\"id\": \"admin4\", \"authKey\": \"1234\"}"))
				.andExpect(status().isBadRequest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals("계정이 없습니다.", e.getCause().getMessage());
		}
	}
}
