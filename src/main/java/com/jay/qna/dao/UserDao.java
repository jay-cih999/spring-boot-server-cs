package com.jay.qna.dao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDao {
	@NotBlank(message = "ID는 필수 입력입니다.")
	@Size(min=1, max=255)
	public String id;
	
	@Size(min=1, max=255)
	public String name;
	
	@NotBlank(message = "비밀번호는 필수 입력입니다.")
	@Size(min=1, max=255)
	public String authKey;
}
