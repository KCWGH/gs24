package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {

	int insertNotice(NoticeVO notice);

	List<NoticeVO> selectListByNotice();

	NoticeVO selectOneByNotice(int noticeId);

	int updateNotice(NoticeVO notice);

	int deleteNotice(int noticeId);

	List<NoticeVO> selectListByPagination(Pagination pagination);

	int selectTotalCount(Pagination pagination);


}
