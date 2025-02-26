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
public class QuestionVO {
	int questionId; // QUESTION_ID
	String memberId; // MEMBER_ID
	String foodType; // FOOD_TYPE
	String ownerId;
	String questionTitle; // QUESTION_TITLE
	String questionContent; // QUESTION_CONTNET
	Date questionDateCreated; // QUESTION_DATE_CREATED
	int isAnswered; // IS_ANSWERED
	boolean questionSecret; // QUESTION_SECRET
	
	List<QuestionAttach> questionAttachList; // QUESTION_ATTACH_LIST
	
	public List<QuestionAttach> getQuestionAttachList() {
        if (questionAttachList == null) {
            questionAttachList = new ArrayList<>(); // 빈 리스트로 초기화
        }
        return questionAttachList;
    }
}
