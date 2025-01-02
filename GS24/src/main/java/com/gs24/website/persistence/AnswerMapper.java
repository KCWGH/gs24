package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
<<<<<<< HEAD
	int insertAnswer(AnswerVO answerVO); // 답변 등록
	
	List<AnswerVO> selectAnswerListByQuestionId(int questionId); // 답변 리스트
	
	int updateAnswer(AnswerVO answerVO); // 답변 수정
	
	int deleteAnswer(int answerId); // 답변 삭제
=======
	int insert(AnswerVO answerVO); // 답변 등록
	
	List<AnswerVO> selectListByQuestionId(int questionId); // 답변 리스트
	
	int update(AnswerVO answerVO); // 답변 수정
	
	int delete(int answerId); // 답변 삭제
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6

}
