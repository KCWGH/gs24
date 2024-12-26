package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
<<<<<<< Updated upstream
	int createNotice(NoticeVO noticeVO); // °Ô½Ã±Û µî·Ï
	//createNotice	

	List<NoticeVO> getAllNotice(); // ÀüÃ¼ °Ô½Ã±Û Á¶È¸
	//getAllNotice

	NoticeVO getNoticeById(int noticeId); // Æ¯Á¤ °Ô½Ã±Û Á¶È¸
	// getNoticeById
	int updateNotice(NoticeVO noticeVO); // Æ¯Á¤ °Ô½Ã±Û ¼öÁ¤
	// updateNotice

	int deleteNotice(int noticeId); // Æ¯Á¤ °Ô½Ã±Û »èÁ¦
	// deleteNotice

	List<NoticeVO>  getPagingNotices(Pagination pagination); // ÀüÃ¼ °Ô½Ã±Û ÆäÀÌÂ¡ Ã³¸®

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
    
    List<NoticeVO> getNoticesByTitle(String noticeTitle); // ì œëª©ìœ¼ë¡œ ê²Œì‹œê¸€ ê²€ìƒ‰
    
    List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination); // ì œëª© ê¸°ë°˜ í˜ì´ì§• ì²˜ë¦¬
    
    int getTotalCountByTitle(String noticeTitle); // ì œëª© ê²€ìƒ‰ ê²°ê³¼ ì´ ê°œìˆ˜
    
    List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination); // ë‚´ìš© ê¸°ë°˜ í˜ì´ì§• ì²˜ë¦¬
    
    int getTotalCountByContent(String noticeContent); // ë‚´ìš© ê²€ìƒ‰ ê²°ê³¼ ì´ ê°œìˆ˜
    
}
>>>>>>> Stashed changes
