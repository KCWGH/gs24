package com.gs24.website.domain;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberVO implements Serializable {
	private static final long serialVersionUID = 2L;
	String memberId;
	String password;
	String email;
	String phone;
	Date birthday;
	int isEnabled;
}
