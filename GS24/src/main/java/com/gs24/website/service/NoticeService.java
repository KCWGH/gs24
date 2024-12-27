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
	
	

=======
	
    int createNotice(NoticeVO noticeVO);
    
    List<NoticeVO> getAllNotice();
    
    NoticeVO getNoticeById(int noticeId);
    
    int updateNotice(NoticeVO noticeVO);
    
    int deleteNotice(int noticeId);
    
    List<NoticeVO> getPagingNotices(Pagination pagination);
    
    int getTotalCount();
    
    List<NoticeVO> getNoticesByTitle(String noticeTitle); 
    
    List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination); 
    
    int getTotalCountByTitle(String noticeTitle); 
    
    List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination); 
    
    int getTotalCountByContent(String noticeContent); 
    
>>>>>>> Stashed changes
}
