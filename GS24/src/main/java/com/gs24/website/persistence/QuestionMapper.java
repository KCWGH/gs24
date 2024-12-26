package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionMapper {
	int insert(QuestionVO questionVO); // 게시글 등록
	
	List<QuestionVO> selectList(); // 전체 게시글 조회
	
	QuestionVO selectOne(int noticeId); // 특정 게시글 조회
	
	int update(QuestionVO questionVO); // 특정 게시글 수정
	
	int delete(int noticeId); // 특정 게시글 삭제
	
	List<QuestionVO> selectListByPagination(Pagination pagination); // 전체 게시글 페이징 처리
	
	int selectTotalCount();
	
	int updateAnswer(int questionId); //답변 상태 변경
}
