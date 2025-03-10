package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
	int insertAnswer(AnswerVO answerVO);
	
	List<AnswerVO> selectAnswerListByQuestionId(int questionId);
	
	int updateAnswer(AnswerVO answerVO);
	
	int deleteAnswer(int answerId);

}
