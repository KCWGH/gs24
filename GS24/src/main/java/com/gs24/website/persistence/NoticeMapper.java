package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.NoticeVO;
import com.gs24.website.util.Pagination;

@Mapper
public interface NoticeMapper {
<<<<<<< HEAD
	int insert(NoticeVO vo); // °Ô½Ã±Û µî·Ï
	
	List<NoticeVO> selectList(); // ÀüÃ¼ °Ô½Ã±Û Á¶È¸
	
	NoticeVO selectOne(int noticeId); // Æ¯Á¤ °Ô½Ã±Û Á¶È¸
	
	int update(NoticeVO vo); // Æ¯Á¤ °Ô½Ã±Û ¼öÁ¤
	
	int delete(int noticeId); // Æ¯Á¤ °Ô½Ã±Û »èÁ¦
	
	List<NoticeVO> selectListByPagination(Pagination pagination); // ÀüÃ¼ °Ô½Ã±Û ÆäÀÌÂ¡ Ã³¸®
	
	int selectTotalCount();
}
=======
	   
		int insert(NoticeVO vo); // ê²Œì‹œê¸€ ë“±ë¡
		
		List<NoticeVO> selectList(); // ì „ì²´ ê²Œì‹œê¸€ ì¡°íšŒ
		
		NoticeVO selectOne(int noticeId); // íŠ¹ì • ê²Œì‹œê¸€ ì¡°íšŒ
		
		int update(NoticeVO vo); // íŠ¹ì • ê²Œì‹œê¸€ ìˆ˜ì •
		
		int delete(int noticeId); // íŠ¹ì • ê²Œì‹œê¸€ ì‚­ì œ
		
		List<NoticeVO> selectListByPagination(Pagination pagination); // ì „ì²´ ê²Œì‹œê¸€ í˜ì´ì§• ì²˜ë¦¬
		
		int selectTotalCount();
}
>>>>>>> 5f0e7c57d0a4abf29e5d76e4b4e2974567c8a0d7
