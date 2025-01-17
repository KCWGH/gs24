package com.gs24.website.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.domain.QuestionAttach;
import com.gs24.website.domain.QuestionAttachDTO;
import com.gs24.website.domain.QuestionDTO;
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
	public int createQuestion(QuestionDTO questionDTO) {	
		log.info("createQuestion()");
		log.info("questionDTO = " + questionDTO);
		int insertQuestionResult = questionMapper.insertQuestion(toEntity(questionDTO));
		log.info(insertQuestionResult + "행 게시글 등록");
	
		List<QuestionAttachDTO> questionAttachList = questionDTO.getQuestionAttachList();

		int insertQuestionAttachResult = 0;
		for(QuestionAttachDTO questionAttachDTO : questionAttachList) {
			insertQuestionAttachResult += questionAttachMapper.insertQuestionAttach(toEntity(questionAttachDTO));
		}
		log.info(insertQuestionAttachResult + "행 파일 정보 등록");
		
		return 1;
	} // end createQuestion()

	@Override
	public List<QuestionDTO> getAllQuestion() {
		log.info("getAllQuestion()");
		List<QuestionVO> list = questionMapper.selectQuestionList();
		return list.stream().map(this::toDTO).collect(Collectors.toList());
	} // end getAllQuestion()

	@Override
	public QuestionDTO getQuestionById(int questionId) {
		log.info("getQuestionById()");
		QuestionVO questionVO = questionMapper.selectQuestionOne(questionId);
		
		List<QuestionAttach> list = questionAttachMapper.selectByQuestionId(questionId);
		
		QuestionDTO questionDTO = toDTO(questionVO);
		log.info(questionDTO);
		
		
		List<QuestionAttachDTO> questionAttachList = list.stream().map(this::toDTO).collect(Collectors.toList());	
		questionDTO.setQuestionAttachList(questionAttachList);
		log.info(questionAttachList);
		return questionDTO;
	} // end getQuestionById()
	
	@Transactional(value = "transactionManager")
	@Override
	public int modifyQuestion(QuestionDTO questionDTO) {
	    log.info("updateQuestion()");
	    log.info("QuestionDTO 내용: " + questionDTO);
	    
	    try {
	        // 1. 기존 질문 내용 업데이트
	        int updateQuestionResult = questionMapper.updateQuestion(toEntity(questionDTO));
	        log.info(updateQuestionResult + "행 게시글 정보 수정");
	        
	        // 2. 기존 첨부 파일 삭제
	        int deleteQuestionAttachResult = questionAttachMapper.deleteQuestionAttach(questionDTO.getQuestionId());
	        log.info(deleteQuestionAttachResult + "행 기존 첨부파일 삭제");
	        
	        // 3. 새로운 첨부 파일 목록 가져오기
	        List<QuestionAttachDTO> questionAttachList = questionDTO.getQuestionAttachList();
	        if (questionAttachList != null && !questionAttachList.isEmpty()) {
	            log.info("새로운 첨부파일 목록: " + questionAttachList);
	            
	            // 4. 새로운 첨부파일들을 데이터베이스에 등록
	            int insertQuestionAttachResult = 0;
	            for (QuestionAttachDTO questionAttachDTO : questionAttachList) {
	                log.info("파일 등록 시작: " + questionAttachDTO);
	                questionAttachDTO.setQuestionId(questionDTO.getQuestionId());
	                insertQuestionAttachResult += questionAttachMapper.insertQuestionAttachModify(toEntity(questionAttachDTO));
	            }
	            log.info(insertQuestionAttachResult + "행 파일 정보 등록");
	        } else {
	            log.info("새로운 첨부파일이 없습니다.");
	        }
	        
	        log.info("modifyQuestion() 끝");
	        return updateQuestionResult;
	    } catch (Exception e) {
	        log.error("첨부파일 수정 중 오류 발생", e);
	        throw e; // 트랜잭션 롤백을 유도
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
	public List<QuestionDTO> getPagingQuestions(Pagination pagination) {
		log.info("getPagingQuestion()");
		List<QuestionVO> list = questionMapper.selectQuestionListByPagination(pagination);
		
		return list.stream().map(this::toDTO).collect(Collectors.toList());
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
	public List<QuestionDTO> getPagedQuestionsByMemberId(String memberId, Pagination pagination) {
		
		return questionMapper.selectQuestionListByPaginationBymemberId(pagination);
	}

	// QuestionVO 데이터를 QuestionDTO에 적용하는 메서드
		public QuestionDTO toDTO(QuestionVO questionVO) {
			QuestionDTO questionDTO = new QuestionDTO();
			questionDTO.setQuestionId(questionVO.getQuestionId());
			questionDTO.setMemberId(questionVO.getMemberId());
			questionDTO.setFoodName(questionVO.getFoodName());
			questionDTO.setQuestionTitle(questionVO.getQuestionTitle());
			questionDTO.setQuestionContent(questionVO.getQuestionContent());
			questionDTO.setQuestionSecret(questionVO.isQuestionSecret());
			questionDTO.setIsAnswered(questionVO.getIsAnswered());
			questionDTO.setQuestionDateCreated(questionVO.getQuestionDateCreated());
			return questionDTO;
		} // end toDTO()
		
		
		// QuestionDTO 데이터를 QuestionVO에 적용하는 메서드
		public QuestionVO toEntity(QuestionDTO questionDTO) {
			QuestionVO questionVO = new QuestionVO();
		    questionVO.setQuestionId(questionDTO.getQuestionId());
		    questionVO.setMemberId(questionDTO.getMemberId());
		    questionVO.setFoodName(questionDTO.getFoodName());
		    questionVO.setQuestionTitle(questionDTO.getQuestionTitle());
		    questionVO.setQuestionContent(questionDTO.getQuestionContent());
		    questionVO.setQuestionSecret(questionDTO.isQuestionSecret());
		    questionVO.setIsAnswered(questionDTO.getIsAnswered());
		    questionVO.setQuestionDateCreated(questionDTO.getQuestionDateCreated());
		    return questionVO;
		} // end toEntity()
		
	    // QuestionAttachDTO를 QuestionAttach로 변환하는 메서드
	    private QuestionAttach toEntity(QuestionAttachDTO questionAttachDTO) {
	    	QuestionAttach questionAttach = new QuestionAttach();
	    	questionAttach.setQuestionAttachId(questionAttachDTO.getQuestionAttachId());
	    	questionAttach.setQuestionId(questionAttachDTO.getQuestionId());
	    	questionAttach.setQuestionAttachPath(questionAttachDTO.getQuestionAttachPath());
	    	questionAttach.setQuestionAttachRealName(questionAttachDTO.getQuestionAttachRealName());
	    	questionAttach.setQuestionAttachChgName(questionAttachDTO.getQuestionAttachChgName());
	    	questionAttach.setQuestionAttachExtension(questionAttachDTO.getQuestionAttachExtension());
	    	questionAttach.setQuestionAttachDateCreated(questionAttachDTO.getQuestionAttachDateCreated());
	        return questionAttach;
	    }
	    
	    // QuestionAttach를 QuestionAttachDTO로 변환하는 메서드
	    private QuestionAttachDTO toDTO(QuestionAttach questionAttach) {
	    	QuestionAttachDTO questionAttachDTO = new QuestionAttachDTO();
	        questionAttachDTO.setQuestionAttachId(questionAttach.getQuestionAttachId());
	        questionAttachDTO.setQuestionId(questionAttach.getQuestionId());
	        questionAttachDTO.setQuestionAttachPath(questionAttach.getQuestionAttachPath());
	        questionAttachDTO.setQuestionAttachRealName(questionAttach.getQuestionAttachRealName());
	        questionAttachDTO.setQuestionAttachChgName(questionAttach.getQuestionAttachChgName());
	        questionAttachDTO.setQuestionAttachExtension(questionAttach.getQuestionAttachExtension());
	        questionAttachDTO.setQuestionAttachDateCreated(questionAttach.getQuestionAttachDateCreated());
	        return questionAttachDTO;
	    }
}
