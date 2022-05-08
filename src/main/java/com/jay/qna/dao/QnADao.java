package com.jay.qna.dao;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnADao {
	public Long id;
	
	@NotNull @NotBlank(message = "사용자ID는 필수 입력입니다.")
	@Size(min=1, max=255)
	public String customer_id;
	
	@NotNull @NotBlank(message = "이름은 필수 입력입니다.")
	@Size(min=1, max=255)
	public String name;
	
	@NotNull @NotBlank(message = "제목은 필수 입력입니다.")
	@Size(min=1, max=255)
	public String title;
	public String contents;
	public Date createdAt;
	public Date updatedAt;
	public String status;
	public String managerId;
	public String assignedAt;
	public String managerName;
	@NotBlank(message = "password 는 필수 입력입니다.")
	@Size(min=1, max=255)
	public String password;
	
	public AnswerDao answer;
	
	public List<String> statusList;
	
	public Date answeredAt;
}
