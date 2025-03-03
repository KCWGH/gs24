package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface QuestionMapper {
	int insertQuestion(QuestionVO questionVO); // 게시글 등록

	List<QuestionVO> selectQuestionList(); // 전체 게시글 조회

	QuestionVO selectQuestionOne(int noticeId); // 특정 게시글 조회

	int updateQuestion(QuestionVO questionVO); // 특정 게시글 수정

	int deleteQuestion(int noticeId); // 특정 게시글 삭제

	List<QuestionVO> selectQuestionListByPagination(Pagination pagination); // 전체 게시글 페이징 처리

	List<QuestionVO> selectPagedQuestionListByOwnerId(Pagination pagination);

	int selectQuestionTotalCount();

	int countTotalQuestionByOwnerId(String ownerId);

	int updateIsAnswered(int questionId); // 답변 상태 1으로 변경

	int deleteIsAnswered(int questionId); // 답변 상태 0으로 변경

	List<QuestionVO> selectQuestionListByMemberId(String memberId);

	List<QuestionVO> selectQuestionListByPaginationBymemberId(Pagination pagination);

	int countQuestionListByMemberId(String memeberId);

}
