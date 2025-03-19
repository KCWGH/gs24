package com.gs24.website.domain;

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
public class OwnerVO {
	String ownerId;
	String password;
	String email;
	String phone;
	String address;
	int isEnabled;
}
