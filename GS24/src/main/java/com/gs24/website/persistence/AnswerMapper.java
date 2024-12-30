package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
	int insertAnswer(AnswerVO answerVO); // �떟蹂� �벑濡�
	
	List<AnswerVO> selectAnswerListByQuestionId(int questionId); // �떟蹂� 由ъ뒪�듃
	
	int updateAnswer(AnswerVO answerVO); // �떟蹂� �닔�젙
	
	int deleteAnswer(int answerId); // �떟蹂� �궘�젣

}
