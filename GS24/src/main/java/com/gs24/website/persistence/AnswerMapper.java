package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
	int insert(AnswerVO answerVO);
	
	List<AnswerVO> selectListByQuestionId(int questionId);
	
	int update(AnswerVO answerVO);
	
	int delete(int answerId);

}
