package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
	   
		int insert(NoticeVO vo);
		
		List<NoticeVO> selectList();
		
		NoticeVO selectOne(int noticeId);
		
		int update(NoticeVO vo);
		
		int delete(int noticeId);
		
		List<NoticeVO> selectListByPagination(Pagination pagination);
		
<<<<<<< Updated upstream
		int selectTotalCount();
=======
		int selectTotalCount(); // 전체 게시글 수
		
		
		List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle); // 제목으로 게시글 검색
		
		List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);

		int selectTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
		
		
		List<NoticeVO> selectListByContent(@Param("noticeContent") String noticeContent); // 내용으로 게시글 검색

		List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);

		int selectTotalCountByContent(@Param("noticeContent") String noticeContent);
>>>>>>> Stashed changes
}
