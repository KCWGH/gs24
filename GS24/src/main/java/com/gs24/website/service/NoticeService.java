package com.gs24.website.service;

import java.util.List;


import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
	int createNotice(NoticeVO noticeVO); // �Խñ� ���
	
	List<NoticeVO> getAllNotice(); // ��ü �Խñ� ��ȸ
	
	NoticeVO getNoticeById(int noticeId); // Ư�� �Խñ� ��ȸ
	
	int updateNotice(NoticeVO noticeVO); // Ư�� �Խñ� ����
	
	int deleteNotice(int noticeId); // Ư�� �Խñ� ����
	
	List<NoticeVO>  getPagingNotices(Pagination pagination); // ��ü �Խñ� ����¡ ó��
	
	int getTotalCount();
}
