package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
   
    int insert(NoticeVO vo);
    
<<<<<<< HEAD
    List<NoticeVO> selectListByNotice();
    
    NoticeVO selectOneByNotice(int noticeId);
=======
    List<NoticeVO> selectList();
    
    NoticeVO selectOne(int noticeId);
    
    int update(NoticeVO vo);
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6
    
    int delete(int noticeId);
    
    List<NoticeVO> selectListByPagination(Pagination pagination);
    
<<<<<<< HEAD
    List<NoticeVO> selectListByPagination(Pagination pagination);
    
    int selectTotalCount();
    
    List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("pagination") Pagination pagination);
=======
    int selectTotalCount();
    
    List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6

    int selectTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByContent(@Param("noticeContent") String noticeContent);
    
<<<<<<< HEAD
    List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("pagination") Pagination pagination);
=======
    List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);
>>>>>>> c10c874fbe2a4197563ffb7d351cdee7e02242a6

    int selectTotalCountByContent(@Param("noticeContent") String noticeContent);
    
    int updateNoticeViews(int noticeId);
}
