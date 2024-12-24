package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
<<<<<<< HEAD
	int createNotice(NoticeVO noticeVO); // °Ô½Ã±Û µî·Ï
	
	List<NoticeVO> getAllNotice(); // ÀüÃ¼ °Ô½Ã±Û Á¶È¸
	
	NoticeVO getNoticeById(int noticeId); // Æ¯Á¤ °Ô½Ã±Û Á¶È¸
	
	int updateNotice(NoticeVO noticeVO); // Æ¯Á¤ °Ô½Ã±Û ¼öÁ¤
	
	int deleteNotice(int noticeId); // Æ¯Á¤ °Ô½Ã±Û »èÁ¦
	
	List<NoticeVO>  getPagingNotices(Pagination pagination); // ÀüÃ¼ °Ô½Ã±Û ÆäÀÌÂ¡ Ã³¸®
	
	int getTotalCount();
=======
	int createNotice(NoticeVO noticeVO); // ê²Œì‹œê¸€ ë“±ë¡
	//createNotice	
	List<NoticeVO> getAllNotice(); // ì „ì²´ ê²Œì‹œê¸€ ì¡°íšŒ
	//getAllNotice
	NoticeVO getNoticeById(int noticeId); // íŠ¹ì • ê²Œì‹œê¸€ ì¡°íšŒ
	// getNoticeById
	int updateNotice(NoticeVO noticeVO); // íŠ¹ì • ê²Œì‹œê¸€ ìˆ˜ì •
	// updateNotice
	int deleteNotice(int noticeId); // íŠ¹ì • ê²Œì‹œê¸€ ì‚­ì œ
	// deleteNotice
	List<NoticeVO>  getPagingNotices(Pagination pagination); // ì „ì²´ ê²Œì‹œê¸€ í˜ì´ì§• ì²˜ë¦¬
	
	int getTotalCount();
	
	

>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7
}
