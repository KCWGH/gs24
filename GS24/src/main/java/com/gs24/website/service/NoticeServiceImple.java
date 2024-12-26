package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.persistence.NoticeMapper;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Service // @Component : Spring�� �����ϴ� ��ü
@Log4j
<<<<<<< Updated upstream
public class NoticeServiceImple implements NoticeService{

   @Autowired
   private NoticeMapper noticeMapper;
   
=======
public class NoticeServiceImple implements NoticeService {
   
   @Autowired
   private NoticeMapper noticeMapper;

>>>>>>> Stashed changes
   @Override
   public int createNotice(NoticeVO vo) {
   	      log.info("createNotice()");
   	      int result = noticeMapper.insert(vo);
   	      return result;	
   }

   @Override
   public List<NoticeVO> getAllNotice() {
   	 log.info("getAllNotice()");
        return noticeMapper.selectList();
   }

   @Override
   public NoticeVO getNoticeById(int noticeId) {
<<<<<<< Updated upstream
   	log.info("getNoticeById()");
       return noticeMapper.selectOne(noticeId);
=======
      log.info("getNoticeById()");
      int result = noticeMapper.updateNoticeViews(noticeId);
      if (result == 1) {
          log.info("조회수 증가 완료: noticeId = " + noticeId);
      } else {
    	  log.info("조회수 증가 실패 ");
      }
      return noticeMapper.selectOne(noticeId);
>>>>>>> Stashed changes
   }

   @Override
   public int updateNotice(NoticeVO vo) {
   	 log.info("updateNotice()");
        return noticeMapper.update(vo);
   }

   @Override
   public int deleteNotice(int noticeId) {
   	 log.info("deleteNotice()");
        return noticeMapper.delete(noticeId);
   }

   @Override
   public List<NoticeVO> getPagingNotices(Pagination pagination) {
   	 log.info("getPagingNotices()");
        return noticeMapper.selectListByPagination(pagination);
   }

   @Override
   public int getTotalCount() {
   	 log.info("getTotalCount()");
        return noticeMapper.selectTotalCount();
   }
<<<<<<< Updated upstream
}
=======

   @Override
   public List<NoticeVO> getNoticesByTitle(String noticeTitle) {
      log.info("searchNoticesByTitle() with title = " + noticeTitle);
      return noticeMapper.selectListByTitle(noticeTitle);
   }

   @Override
   public List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination) {
       int start = (pagination.getPageNum() - 1) * pagination.getPageSize() + 1;
       int end = start + pagination.getPageSize() - 1;
       return noticeMapper.selectListByTitleWithPagination(noticeTitle, start, end);
   }

   @Override
   public int getTotalCountByTitle(String noticeTitle) {
       return noticeMapper.selectTotalCountByTitle(noticeTitle);
   }

   @Override
   public List<NoticeVO> getNoticesByContentWithPagination(String noticeContent, Pagination pagination) {
       int start = (pagination.getPageNum() - 1) * pagination.getPageSize() + 1;
       int end = start + pagination.getPageSize() - 1;
       return noticeMapper.selectListByContentWithPagination(noticeContent, start, end);
   }

   @Override
   public int getTotalCountByContent(String noticeContent) {
       return noticeMapper.selectTotalCountByContent(noticeContent);
   }
}
>>>>>>> Stashed changes
