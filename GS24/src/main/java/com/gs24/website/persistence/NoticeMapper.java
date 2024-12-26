package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
	
	int insert(NoticeVO vo); // 게시글 등록

	List<NoticeVO> selectList(); // 전체 게시글 조회

	NoticeVO selectOne(int noticeId); // 특정 게시글 조회

	int update(NoticeVO vo); // 특정 게시글 수정

	int delete(int noticeId); // 특정 게시글 삭제

	List<NoticeVO> selectListByPagination(Pagination pagination); // 전체 게시글 페이징 처리
	
	int selectTotalCount(); // �쟾泥� 寃뚯떆湲� �닔
	
	
	List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle); // �젣紐⑹쑝濡� 寃뚯떆湲� 寃��깋
	
	List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);

	int selectTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
	
	
	List<NoticeVO> selectListByContent(@Param("noticeContent") String noticeContent); // �궡�슜�쑝濡� 寃뚯떆湲� 寃��깋

	List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);

	int selectTotalCountByContent(@Param("noticeContent") String noticeContent);


}
