package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.AnswerVO;
@Mapper
public interface AnswerMapper {
	
<<<<<<< Updated upstream
	int insertAnswer(AnswerVO answerVO); // �떟蹂� �벑濡�
=======
	int insertAnswer(AnswerVO answerVO); // 답변 등록
>>>>>>> Stashed changes
	
	List<AnswerVO> selectAnswerListByQuestionId(int questionId); // �떟蹂� 由ъ뒪�듃
	
<<<<<<< Updated upstream
	int updateAnswer(AnswerVO answerVO); // �떟蹂� �닔�젙
	
	int deleteAnswer(int answerId); // �떟蹂� �궘�젣
=======
	int updateAnswer(AnswerVO answerVO); // 답변 수정
	
	int deleteAnswer(int answerId); // 답변 삭제
>>>>>>> Stashed changes

}
