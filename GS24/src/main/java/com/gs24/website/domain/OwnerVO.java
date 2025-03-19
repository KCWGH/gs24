package com.gs24.website.domain;

import java.io.Serializable;

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
public class OwnerVO implements Serializable {
	private static final long serialVersionUID = 3L;
	String ownerId;
	String password;
	String email;
	String phone;
	String address;
	int isEnabled;
}
