package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
<<<<<<< Updated upstream
	int createNotice(NoticeVO noticeVO); // 게시글 등록
	//createNotice	

	List<NoticeVO> getAllNotice(); // 전체 게시글 조회
	//getAllNotice

	NoticeVO getNoticeById(int noticeId); // 특정 게시글 조회
	// getNoticeById
	int updateNotice(NoticeVO noticeVO); // 특정 게시글 수정
	// updateNotice

	int deleteNotice(int noticeId); // 특정 게시글 삭제
	// deleteNotice

	List<NoticeVO>  getPagingNotices(Pagination pagination); // 전체 게시글 페이징 처리

	int getTotalCount();
	
}
=======
    int createNotice(NoticeVO noticeVO);
    
    List<NoticeVO> getAllNotice();
    
    NoticeVO getNoticeById(int noticeId);
    
    int updateNotice(NoticeVO noticeVO);
    
    int deleteNotice(int noticeId);
    
    List<NoticeVO> getPagingNotices(Pagination pagination);
    
    int getTotalCount();
    
    List<NoticeVO> getNoticesByTitle(String noticeTitle); // �젣紐⑹쑝濡� 寃뚯떆湲� 寃��깋
    
    List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination); // �젣紐� 湲곕컲 �럹�씠吏� 泥섎━
    
    int getTotalCountByTitle(String noticeTitle); // �젣紐� 寃��깋 寃곌낵 珥� 媛쒖닔
    
    List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination); // �궡�슜 湲곕컲 �럹�씠吏� 泥섎━
    
    int getTotalCountByContent(String noticeContent); // �궡�슜 寃��깋 寃곌낵 珥� 媛쒖닔
    
}
>>>>>>> Stashed changes
