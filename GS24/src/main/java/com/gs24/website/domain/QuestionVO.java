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
public class QuestionVO {
	int questionId; // QUESTION_ID
	String memberId; // MEMBER_ID
	String foodName; // FOOD_NAME
	String questionTitle; // QUESTION_TITLE
	String questionContent; // QUESTION_CONTNET
	Date questionDateCreated; // QUESTION_DATE_CREATED
	int isAnswered; // IS_ANSWERED
	boolean questionSecret; // QUESTION_SECRET
	
}
