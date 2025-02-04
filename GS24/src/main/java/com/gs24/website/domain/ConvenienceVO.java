package com.gs24.website.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Log4j
public class ConvenienceVO {
	private int convenienceId;
	private String ownerId;
	private String address;
	private int isEnabled;
}
