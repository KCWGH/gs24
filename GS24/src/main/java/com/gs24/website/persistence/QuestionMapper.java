package com.gs24.website.persistence;

import java.util.List;

import com.gs24.website.domain.QuestionVO;
import com.gs24.website.util.Pagination;

public interface QuestionMapper {
	int insert(QuestionVO questionVO); // �Խñ� ���
	
	List<QuestionVO> selectList(); // ��ü �Խñ� ��ȸ
	
	QuestionVO selectOne(int noticeId); // Ư�� �Խñ� ��ȸ
	
	int update(QuestionVO questionVO); // Ư�� �Խñ� ����
	
	int delete(int noticeId); // Ư�� �Խñ� ����
	
	List<QuestionVO> selectListByPagination(Pagination pagination); // ��ü �Խñ� ����¡ ó��
	
	int selectTotalCount();
	
	int updateAnswer(int questionId); //�亯 ���� ����
}
