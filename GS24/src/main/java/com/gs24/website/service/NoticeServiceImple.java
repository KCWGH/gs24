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
        return noticeMapper.insertNotice(vo);
    }

    @Override
    public List<NoticeVO> getAllNotice() {
        log.info("getAllNotice()");
        return noticeMapper.selectListByNotice();
    }

    @Override
    public NoticeVO getNoticeById(int noticeId) {
        log.info("getNoticeById()");
        return noticeMapper.selectOneByNotice(noticeId);
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
        return noticeMapper.selectListByPagination(pagination);
    }

    @Override
    public int getTotalCount(Pagination pagination) {
        log.info("getTotalCount()");
        return noticeMapper.selectTotalCount(pagination);
    }

   

}
