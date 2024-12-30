package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
   
    int insertNotice(NoticeVO vo);
    
    List<NoticeVO> selectNoticeList();
    
    NoticeVO selectNoticeOne(int noticeId);
    
    int updateNotice(NoticeVO vo);
    
    int deleteNotice(int noticeId);
    
    List<NoticeVO> selectNoticeListByPagination(Pagination pagination);
    
    int selectNoticeTotalCount();
    
    List<NoticeVO> selectNoticeListByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectNoticeListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);

    int selectNoticeTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectNoticeListByContent(@Param("noticeContent") String noticeContent);
    
    List<NoticeVO> selectNoticeListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);

    int selectNoticeTotalCountByContent(@Param("noticeContent") String noticeContent);
    
    int updateNoticeViews(int noticeId);
}
