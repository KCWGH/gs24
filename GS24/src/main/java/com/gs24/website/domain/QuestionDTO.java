package com.gs24.website.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter 
@Setter
@ToString 
public class QuestionDTO {
	int questionId; // QUESTION_ID
	String memberId; // MEMBER_ID
	String foodName; // FOOD_NAME
	String questionTitle; // QUESTION_TITLE
	String questionContent; // QUESTION_CONTNET
	Date questionDateCreated; // QUESTION_DATE_CREATED
	int isAnswered; // IS_ANSWERED
	boolean questionSecret; // QUESTION_SECRET
	
	private List<QuestionAttachDTO> questionAttachList;
	
	public List<QuestionAttachDTO> getQuestionAttachList() {
		if(questionAttachList == null) {
			questionAttachList = new ArrayList<QuestionAttachDTO>();
		}
		return questionAttachList;
	}
}

