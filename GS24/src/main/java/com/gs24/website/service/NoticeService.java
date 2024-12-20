package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
	int createNotice(NoticeVO noticeVO);
	//createNotice	
	List<NoticeVO> getAllNotice();
	//getAllNotice
	NoticeVO getNoticeById(int noticeId);
	// getNoticeById
	int updateNotice(NoticeVO noticeVO);
	// updateNotice
	int deleteNotice(int noticeId);
	// deleteNotice
	List<NoticeVO>  getPagingNotices(Pagination pagination);
	
	int getTotalCount();
	
	

}
