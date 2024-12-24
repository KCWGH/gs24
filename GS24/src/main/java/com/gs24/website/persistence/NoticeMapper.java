package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
	int insert(NoticeVO vo); // �Խñ� ���
	
	List<NoticeVO> selectList(); // ��ü �Խñ� ��ȸ
	
	NoticeVO selectOne(int noticeId); // Ư�� �Խñ� ��ȸ
	
	int update(NoticeVO vo); // Ư�� �Խñ� ����
	
	int delete(int noticeId); // Ư�� �Խñ� ����
	
	List<NoticeVO> selectListByPagination(Pagination pagination); // ��ü �Խñ� ����¡ ó��
	
	int selectTotalCount();
}