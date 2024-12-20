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
public class NoticeServiceImple implements NoticeService{
   
   @Autowired
   private NoticeMapper noticeMapper;

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
	log.info("getNoticeById()");
    return noticeMapper.selectOne(noticeId);
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

}
