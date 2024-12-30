package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
   
    int insertNotice(NoticeVO vo);
    
<<<<<<< Updated upstream
    List<NoticeVO> selectNoticeList();
    
    NoticeVO selectNoticeOne(int noticeId);
=======
    List<NoticeVO> selectListByNotice();
    
    NoticeVO selectOneByNotice(int noticeId);
>>>>>>> Stashed changes
    
    int updateNotice(NoticeVO vo);
    
    int deleteNotice(int noticeId);
    
    List<NoticeVO> selectNoticeListByPagination(Pagination pagination);
    
    int selectNoticeTotalCount();
    
    List<NoticeVO> selectNoticeListByTitle(@Param("noticeTitle") String noticeTitle);
    
<<<<<<< Updated upstream
    List<NoticeVO> selectNoticeListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("start") int start, @Param("end") int end);
=======
    List<NoticeVO> selectListByTitleWithPagination(@Param("noticeTitle") String noticeTitle, @Param("pagination") Pagination pagination);
>>>>>>> Stashed changes

    int selectNoticeTotalCountByTitle(@Param("noticeTitle") String noticeTitle);
    
    List<NoticeVO> selectNoticeListByContent(@Param("noticeContent") String noticeContent);
    
<<<<<<< Updated upstream
    List<NoticeVO> selectNoticeListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("start") int start, @Param("end") int end);
=======
    List<NoticeVO> selectListByContentWithPagination(@Param("noticeContent") String noticeContent, @Param("pagination") Pagination pagination);
>>>>>>> Stashed changes

    int selectNoticeTotalCountByContent(@Param("noticeContent") String noticeContent);
    
    int updateNoticeViews(int noticeId);
}
