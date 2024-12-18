package com.gs24.website.service;

import java.util.List;
import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

public interface NoticeService {
    int createNotice(NoticeVO noticeVO); // 게시글 등록

    List<NoticeVO> getAllNotice(); // 전체 게시글 조회

    NoticeVO getNoticeById(int noticeId); // 특정 게시글 조회

    int updateNotice(NoticeVO noticeVO); // 특정 게시글 수정

    int deleteNotice(int noticeId); // 특정 게시글 삭제

    List<NoticeVO> getPagingNotices(Pagination pagination); // 전체 게시글 페이징 처리

    int getTotalCount(); // 전체 게시글 수 가져오기

    List<NoticeVO> getNoticesByTitle(String noticeTitle); // 제목으로 게시글 검색

    List<NoticeVO> getNoticesByTitleWithPagination(String noticeTitle, Pagination pagination); // 제목 기반 페이징 처리

    int getTotalCountByTitle(String noticeTitle); // 제목 검색 결과 총 개수
}
