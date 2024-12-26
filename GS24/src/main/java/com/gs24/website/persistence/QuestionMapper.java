package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionMapper {
	int insert(QuestionVO questionVO); 
	
	List<QuestionVO> selectList(); 
	
	QuestionVO selectOne(int noticeId); 
	
	int update(QuestionVO questionVO); 
	
	int delete(int noticeId); 
	
	List<QuestionVO> selectListByPagination(Pagination pagination); 
	
	int selectTotalCount();
	
	int updateAnswer(int questionId);

	List<QuestionVO> selectQuestionListByMemberId(String memberId); 
}
