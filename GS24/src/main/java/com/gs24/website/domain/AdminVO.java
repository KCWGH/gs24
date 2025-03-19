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
public class AdminVO implements Serializable {
	private static final long serialVersionUID = 4L;
	private String adminId;
	private String password;
}
