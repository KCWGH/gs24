package com.gs24.website.check;

import org.springframework.security.core.Authentication;

import lombok.extern.log4j.Log4j;

@Log4j
public class CheckData {

//	@Autowired
//	private ReviewMapper reviewMapper;
//
//	@Autowired
//	private ConvenienceFoodMapper convenienceFoodMapper;
//
//	@Autowired
//	private FoodListMapper foodListMapper;
//
//	@Autowired
//	private CheckReviewData checkReviewData;

	/**
	 * 로그인한 회원 ID와 확인할 ID가 맞는지 확인하는 메소드
	 * 
	 * @param auth     : 로그인 정보
	 * @param memberId : 확인할 회원 ID
	 * @return true : 값이 일치할 때 | false : 값이 일치하지 않거나 매개변수에 null이 하나라도 있을 때
	 */
	public static boolean checkUserName(Authentication auth, String memberId) {
		log.info("checkUserName()");
		boolean checkName = false;
		try {
			checkName = auth.getName().equals(memberId.toString());
		} catch (NullPointerException e) {
			log.info((auth == null ? "auth parameter is null" : "auth parameter is not null"));
			log.info((memberId == null ? "memberId parameter is null" : "memberId parameter is not null"));
			return false;
		}

		log.info("checkUserName is " + checkName);

		if (!checkName)
			log.info("memberId : " + memberId);
		return checkName;
	}
}
