package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface QuestionMapper {
	int insertQuestion(QuestionVO questionVO); 

	List<QuestionVO> selectQuestionList(); 

	QuestionVO selectQuestionOne(int noticeId); 

	int updateQuestion(QuestionVO questionVO); 

	int deleteQuestion(int noticeId); 

	List<QuestionVO> selectQuestionListByPagination(Pagination pagination); 

	List<QuestionVO> selectPagedQuestionListByOwnerId(Pagination pagination);

	int selectQuestionTotalCount();

	int countTotalQuestionByOwnerId(String ownerId);

	int updateIsAnswered(int questionId); 

	int deleteIsAnswered(int questionId); 
	
	List<QuestionVO> selectQuestionListByMemberId(String memberId);

	List<QuestionVO> selectQuestionListByPaginationBymemberId(Pagination pagination);

	int countQuestionListByMemberId(String memeberId);

}
