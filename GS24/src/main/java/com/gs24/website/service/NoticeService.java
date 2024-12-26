package com.gs24.website.service;

import java.util.List;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
<<<<<<< Updated upstream
	int createNotice(NoticeVO noticeVO); // �Խñ� ���
	//createNotice	

	List<NoticeVO> getAllNotice(); // ��ü �Խñ� ��ȸ
	//getAllNotice

	NoticeVO getNoticeById(int noticeId); // Ư�� �Խñ� ��ȸ
	// getNoticeById
	int updateNotice(NoticeVO noticeVO); // Ư�� �Խñ� ����
	// updateNotice

	int deleteNotice(int noticeId); // Ư�� �Խñ� ����
	// deleteNotice

	List<NoticeVO>  getPagingNotices(Pagination pagination); // ��ü �Խñ� ����¡ ó��

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
    
    List<NoticeVO> getNoticesByTitle(String noticeTitle); // 제목으로 게시글 검색
    
    List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination); // 제목 기반 페이징 처리
    
    int getTotalCountByTitle(String noticeTitle); // 제목 검색 결과 총 개수
    
    List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination); // 내용 기반 페이징 처리
    
    int getTotalCountByContent(String noticeContent); // 내용 검색 결과 총 개수
    
}
>>>>>>> Stashed changes
