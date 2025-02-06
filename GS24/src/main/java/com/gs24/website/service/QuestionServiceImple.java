package com.gs24.website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.QuestionAttach;
import com.gs24.website.domain.QuestionVO;
import com.gs24.website.persistence.QuestionAttachMapper;
import com.gs24.website.persistence.QuestionMapper;
import com.gs24.website.util.Pagination;


import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class QuestionServiceImple implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private QuestionAttachMapper questionAttachMapper;
	
	@Transactional(value = "transactionManager") 
	@Override
	public int createQuestion(QuestionVO questionVO) {	
		log.info("createQuestion()");
		log.info("questionVO = " + questionVO);
		int insertQuestionResult = questionMapper.insertQuestion(questionVO);
		log.info(insertQuestionResult + "행 게시글 등록");
	
		List<QuestionAttach> questionAttachList = questionVO.getQuestionAttachList();
		
		if (questionAttachList == null) {
	        questionAttachList = new ArrayList<>();
	        log.info("첨부파일이 없습니다. 빈 리스트로 처리합니다.");
	    }
		
		int insertQuestionAttachResult = 0;
		for(QuestionAttach questionAttach : questionAttachList) {
			insertQuestionAttachResult += questionAttachMapper.insertQuestionAttach(questionAttach);
		}
		log.info(insertQuestionAttachResult + "행 파일 정보 등록");
		
		
		return 1;
	} // end createQuestion()

	@Override
	public List<QuestionVO> getAllQuestion() {
		log.info("getAllQuestion()");
		List<QuestionVO> list = questionMapper.selectQuestionList();
		return list;
	} // end getAllQuestion()

	@Override
	public QuestionVO getQuestionById(int questionId) {
		log.info("getQuestionById()");
		QuestionVO questionVO = questionMapper.selectQuestionOne(questionId);
		
		List<QuestionAttach> list = questionAttachMapper.selectByQuestionId(questionId);
		questionVO.setQuestionAttachList(list);
		log.info(questionVO);
		
		
		
		return questionVO;
	} // end getQuestionById()
	
	@Transactional(value = "transactionManager")
	@Override
	public int modifyQuestion(QuestionVO questionVO) {
	    log.info("updateQuestion()");
	    log.info("QuestionVO 내용: " + questionVO);
	    
	    try {
	        // 1. 기존 질문 내용 업데이트
	        int updateQuestionResult = questionMapper.updateQuestion(questionVO);
	        log.info(updateQuestionResult + "행 게시글 정보 수정");
	        
	        // 2. 기존 첨부 파일 삭제
	        int deleteQuestionAttachResult = questionAttachMapper.deleteQuestionAttach(questionVO.getQuestionId());
	        log.info(deleteQuestionAttachResult + "행 기존 첨부파일 삭제");
	        
	        // 3. 새로운 첨부 파일 목록 가져오기
	        List<QuestionAttach> questionAttachList = questionVO.getQuestionAttachList();
	        if (questionAttachList != null && !questionAttachList.isEmpty()) {
	            log.info("새로운 첨부파일 목록: " + questionAttachList);
	            
	            // 4. 새로운 첨부파일들을 데이터베이스에 등록
	            int insertQuestionAttachResult = 0;
	            for (QuestionAttach questionAttach : questionAttachList) {
	                log.info("파일 등록 시작: " + questionAttach);
	                questionAttach.setQuestionId(questionVO.getQuestionId());
	                insertQuestionAttachResult += questionAttachMapper.insertQuestionAttachModify(questionAttach);
	            }
	            log.info(insertQuestionAttachResult + "행 파일 정보 등록");
	        } else {
	            log.info("새로운 첨부파일이 없습니다.");
	        }
	        
	        log.info("modifyQuestion() 끝");
	        return updateQuestionResult;
	    } catch (Exception e) {
	        log.error("첨부파일 수정 중 오류 발생", e);
	        throw e; 
	    }
	} // end updateQuestion()

	@Transactional(value = "transactionManager")
	@Override
	public int deleteQuestion(int questionId) {
		log.info("getNoticeById()");
		log.info("questionId" + questionId);
		int deleteQuestionResult = questionMapper.deleteQuestion(questionId);
		log.info(deleteQuestionResult + "행 게시글 정보 삭제");
		int deleteQuestionAttachResult = questionAttachMapper.deleteQuestionAttach(questionId);
		log.info(deleteQuestionAttachResult + "행 파일 정보 삭제");
		return 1;
	} // end deleteQuestion()

	@Override
	public List<QuestionVO> getPagingQuestions(Pagination pagination) {
		log.info("getPagingQuestion()");
		List<QuestionVO> list = questionMapper.selectQuestionListByPagination(pagination);	
		return list;
	} // end getPagingQuestions()

	@Override
	public int getTotalCount() {
		log.info("getTotalCount()");
		return questionMapper.selectQuestionTotalCount();
	}

	@Override
	public int countQuestionByMemberId(String memberId) {
		return questionMapper.countQuestionListByMemberId(memberId);
	}
	
	@Override
	public List<QuestionVO> getQuestionListByMemberId(String memberId) {
		
		return questionMapper.selectQuestionListByMemberId(memberId);
	}
	
	@Override
	public List<QuestionVO> getPagedQuestionsByMemberId(String memberId, Pagination pagination) {
		
		return questionMapper.selectQuestionListByPaginationBymemberId(pagination);
	}

	@Override
	public List<QuestionVO> getQuestionListByOwnerId(String ownerId) {
		return questionMapper.selectQuestionListByOwnerId(ownerId);
	}	

}
