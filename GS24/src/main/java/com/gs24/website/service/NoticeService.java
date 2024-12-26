package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
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