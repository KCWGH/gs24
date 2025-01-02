package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
   
    int insertNotice(NoticeVO vo);
    
    List<NoticeVO> selectListByNotice();
    
    NoticeVO selectOneByNotice(int noticeId);
    
    int updateNotice(NoticeVO vo);
    
    int deleteNotice(int noticeId);
    
    List<NoticeVO> selectListByPagination(Pagination pagination);
    
    int selectTotalCount();
    
    List<NoticeVO> selectListByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("pagination") Pagination pagination);

    int selectTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectListByContent(@Param("noticeContent") String noticeContent);
    
    List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("pagination") Pagination pagination);

    int selectTotalCountByContent(@Param("noticeContent") String noticeContent);
    
    int updateNoticeViews(int noticeId);
}
