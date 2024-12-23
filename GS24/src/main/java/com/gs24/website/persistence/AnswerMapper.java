package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
	int insert(AnswerVO answerVO); // 답변 등록
	
	List<AnswerVO> selectListByQuestionId(int questionId); // 답변 리스트
	
	int update(AnswerVO answerVO); // 답변 수정
	
	int delete(int answerId); // 답변 삭제

}
