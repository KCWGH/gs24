package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
<<<<<<< HEAD
	int createNotice(NoticeVO noticeVO); // �Խñ� ���
	
	List<NoticeVO> getAllNotice(); // ��ü �Խñ� ��ȸ
	
	NoticeVO getNoticeById(int noticeId); // Ư�� �Խñ� ��ȸ
	
	int updateNotice(NoticeVO noticeVO); // Ư�� �Խñ� ����
	
	int deleteNotice(int noticeId); // Ư�� �Խñ� ����
	
	List<NoticeVO>  getPagingNotices(Pagination pagination); // ��ü �Խñ� ����¡ ó��
	
	int getTotalCount();
=======
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
	
	

>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7
}
