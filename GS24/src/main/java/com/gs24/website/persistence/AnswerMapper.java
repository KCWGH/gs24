package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
	int insertAnswer(AnswerVO answerVO); // 답변 등록
	
	List<AnswerVO> selectAnswerListByQuestionId(int questionId); // 답변 리스트
	
	int updateAnswer(AnswerVO answerVO); // 답변 수정
	
	int deleteAnswer(int answerId); // 답변 삭제

}
