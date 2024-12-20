package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
	   
		int insert(NoticeVO vo);
		
		List<NoticeVO> selectList();
		
		NoticeVO selectOne(int noticeId);
		
		int update(NoticeVO vo);
		
		int delete(int noticeId);
		
		List<NoticeVO> selectListByPagination(Pagination pagination);
		
		int selectTotalCount();
}
