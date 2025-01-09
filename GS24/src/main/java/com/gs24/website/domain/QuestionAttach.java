package com.gs24.website.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter 
@Setter
@ToString 
public class QuestionAttach {
	private int questionAttachId;
	private int questionId;
	private String questionAttachPath;
	private String questionAttachRealName;
	private String questionAttachChgName;
	private String questionAttachExtension;
	private Date questionAttachDateCreated;
}
