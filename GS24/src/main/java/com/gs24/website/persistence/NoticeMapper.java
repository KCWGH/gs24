package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
<<<<<<< Updated upstream
	   
		int insert(NoticeVO vo); // 게시글 등록
		
		List<NoticeVO> selectList(); // 전체 게시글 조회
		
		NoticeVO selectOne(int noticeId); // 특정 게시글 조회
		
		int update(NoticeVO vo); // 특정 게시글 수정
		
		int delete(int noticeId); // 특정 게시글 삭제
		
		List<NoticeVO> selectListByPagination(Pagination pagination); // 전체 게시글 페이징 처리
		
		int selectTotalCount();
=======
   
    int insert(NoticeVO vo);
    
    List<NoticeVO> selectList();
    
    NoticeVO selectOne(int noticeId);
    
    int update(NoticeVO vo);
    
    int delete(int noticeId);
    
    List<NoticeVO> selectListByPagination(Pagination pagination);
    
    int selectTotalCount();
    
    List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);

    int selectTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByContent(@Param("noticeContent") String noticeContent);
    
    List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);

    int selectTotalCountByContent(@Param("noticeContent") String noticeContent);
    
    int updateNoticeViews(int noticeId);
>>>>>>> Stashed changes
}
