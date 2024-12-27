package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
	
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
    
}
