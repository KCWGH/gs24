package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.persistence.NoticeMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class NoticeServiceImple implements NoticeService {
   
   @Autowired
   private NoticeMapper noticeMapper;

   @Override
   public int createNotice(NoticeVO vo) {
      log.info("createNotice()");
      int result = noticeMapper.insertNotice(vo);
      return result;  
   }

   @Override
   public List<NoticeVO> getAllNotice() {
      log.info("getAllNotice()");
      return noticeMapper.selectNoticeList();
   }

   @Override
   public NoticeVO getNoticeById(int noticeId) {
      log.info("getNoticeById()");
      int result = noticeMapper.updateNoticeViews(noticeId);
      if (result == 1) {
          log.info("views up : noticeId = " + noticeId);
      } else {
    	  log.info("views up fail");
      }
      return noticeMapper.selectNoticeOne(noticeId);
   }

   @Override
   public int updateNotice(NoticeVO vo) {
      log.info("updateNotice()");
      return noticeMapper.updateNotice(vo);
   }

   @Override
   public int deleteNotice(int noticeId) {
      log.info("deleteNotice()");
      return noticeMapper.deleteNotice(noticeId);
   }

   @Override
   public List<NoticeVO> getPagingNotices(Pagination pagination) {
      log.info("getPagingNotices()");
      return noticeMapper.selectNoticeListByPagination(pagination);  // pagination 揶쏆빘猿� 域밸챶占썸에占� 占쎌읈占쎈뼎
   }

   @Override
   public int getTotalCount() {
      log.info("getTotalCount()");
      return noticeMapper.selectNoticeTotalCount();
   }

   @Override
   public List<NoticeVO> getNoticesByTitle(String noticeTitle) {
      log.info("searchNoticesByTitle() with title = " + noticeTitle);
      return noticeMapper.selectNoticeListByTitle(noticeTitle);
   }

   @Override
   public List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination) {
       int start = (pagination.getPageNum() - 1) * pagination.getPageSize() + 1;
       int end = start + pagination.getPageSize() - 1;
       return noticeMapper.selectNoticeListByTitleWithPagination(noticeTitle, start, end);
   }

   @Override
   public int getTotalCountByTitle(String noticeTitle) {
       return noticeMapper.selectNoticeTotalCountByTitle(noticeTitle);
   }

   @Override
   public List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination) {
       int start = (pagination.getPageNum() - 1) * pagination.getPageSize() + 1;
       int end = start + pagination.getPageSize() - 1;
       return noticeMapper.selectNoticeListByContentWithPagination(noticeContent, start, end);
   }

   @Override
   public int getTotalCountByContent(String noticeContent) {
       return noticeMapper.selectNoticeTotalCountByContent(noticeContent);
   }
}
