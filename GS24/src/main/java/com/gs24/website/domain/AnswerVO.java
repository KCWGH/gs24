package com.gs24.website.domain;

import java.util.Date;

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
public class AnswerVO {
	int answerId; // ANSWER_ID
	int questionId; // QUESTION_ID
	String memberId; // MEMBER_ID
	String answerContent; // ANSWER_CONTENT
	Date answerDateCreated; // ANSWER_DATE_CREATED
}
