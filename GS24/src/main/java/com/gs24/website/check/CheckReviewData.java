package com.gs24.website.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gs24.website.domain.ReviewVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.ReviewMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class CheckReviewData {
	
	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	/**
	 * 리뷰데이터를 확인하는 메소드
	 * 
	 * @param reviewVO 리뷰 데이터
	 * @param convenienceId 각 편의점 ID
	 * @return true : 모두 리뷰 데이터가 정상일 때 | false : 리뷰 데이터 중 하나라도 비정상 일때
	 */
	public boolean checkReviewData(Authentication auth,ReviewVO reviewVO, int convenienceId) {
		boolean check = true;
		
		// 각 메소드의 반환값이 하나라도 false면 false값을 반환하도록 논리연산을 사용
		try {
			switch (checkReviewId(reviewVO.getReviewId())) {
			case 0:
				log.info("없는 리뷰 입니다.");
				return false;
			case 1:
				log.info("신규 리뷰 이거나 기존 리뷰 입니다.");
				check = check && checkReviewMemberId(reviewVO.getMemberId(), reviewVO.getFoodId());
				break;
			case 2:
				log.info("신규 리뷰 입니다.");
				check = check && CheckData.checkUserName(auth, reviewVO.getMemberId()); 
			}
			// 그 편의점이 갖고 있는 식품인지 확인하는 코드
			check = check && checkReviewFoodId(reviewVO.getFoodId(),convenienceId);
			// 리뷰 내용이 500자 이상인지 확인하는 코드
			check = check && checkReviewContent(reviewVO.getReviewContent());
			// 리뷰 제목이 200자 이상인지 확인하는 코드
			check = check && checkReviewTitle(reviewVO.getReviewTitle());
			// 리뷰 점수가 1이상 5 이하인지 확인하는 코드
			check = check && checkReviewRating(reviewVO.getReviewRating());
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		
		log.info(check);
		return check;
	}
	public int checkReviewId(int reviewId) {
		int check = 0;
		
		//반환값 0 : 존재할 수 없는 리뷰 | 1 : 기존 리뷰일 수도 있는 것 | 2 : 신규 등록 리뷰
		try {
			if(reviewId < 0) {
				check = 0;
			} else if(reviewId > 0) {
				check =  (reviewMapper.selectReviewByReviewId(reviewId) == null ? 0 : 1);
			} else {
				check = 2;
			}
		}catch (Exception e) {
			log.info(e.getMessage());
			return 0;
		}
		
		return check;
	}
	
	public boolean checkReviewMemberId(String memberId, int foodId) {
		boolean check = false;
		
		try {
			check = (reviewMapper.hasReview(memberId, foodId) == 1 ? true : false);
		}catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		
		log.info("checkReviewMemberId is " + check);
		if(!check)
			log.info("hasReview method : null");
		
		return check;
	}
	
	public boolean checkReviewFoodId(int foodId, int convenienceId) {
		
		boolean check = false;
		
		try {
			check = (convenienceFoodMapper.checkHasFood(foodId, convenienceId) == 1 ? true : false);
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info("checkReviewFoodId is Exception");
			return false;
		}
		
		log.info("checkReviewFoodId is " + check);
		if(!check)
			log.info("foodId : " + foodId + " convenienceId : " + convenienceId);
		
		return check;
	}
	
	public boolean checkReviewContent(String reviewContent) {
		boolean check = false;
		
		try {
			check = (reviewContent.length() > 500 ? false : true);
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("checkReviewContent is Exception");
			return false;
		}
		
		log.info("checkReviewContent is " + check);
		if(!check)
			log.info("review Content length is " + reviewContent.length());
		
		return check;
	}
	
	public boolean checkReviewTitle(String reviewTitle) {
		boolean check = false;
		
		try {
			check = (reviewTitle.length() > 200 ? false : true);
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("checkReviewTitle is Exception");
			return false;
		}
		
		log.info("checkReviewTitle is " + check);
		if(!check)
			log.info("review Content length is " + reviewTitle.length());
		
		return check;
	}
	
	public boolean checkReviewRating(int reviewRaiting) {
		boolean check = true;
		
		try {
			check = (reviewRaiting > 5 ? false : true);
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info("checkReviewRating is Exception");
			return false;
		}
		
		log.info("checkReviewRating is " + check);
		if(!check)
			log.info("review rating is " + reviewRaiting);
		
		return check;
	}
}
