package com.gs24.website.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter 
@Setter
@ToString 
public class ImgQuestionVO {
	int imgQuestionId;
	int questionId;
	String imgQuestionPath;
	String imgQuestionRealName;
	String imgQuestionChgName;
	String imgQuestionExtension;
	Date imgQuestionDateCreated;
	MultipartFile file;
}
