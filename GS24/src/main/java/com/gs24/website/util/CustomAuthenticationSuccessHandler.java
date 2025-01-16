package com.gs24.website.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.gs24.website.domain.GiftCardVO;
import com.gs24.website.service.GiftCardService;
import com.gs24.website.service.MemberService;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private MemberService memberService;

	@Autowired
	private GiftCardService giftCardService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println("onAuthenticationSuccess 호출됨.");
		// 로그인 성공 후 플래시 메시지를 세션에 저장
		HttpSession session = request.getSession();

		// 로그인한 사용자만 처리
		if (authentication != null && authentication.isAuthenticated()) {
			// 세션에 "message"가 이미 존재하는지 확인
			if (session.getAttribute("message") == null) {
				// 생일 쿠폰 발급 처리
				int result = birthdayCoupon(authentication.getName());
				if (result == 1) {
					session.setAttribute("message", "");
				}
			}
		}

		// 기본 리다이렉트 URL을 설정
		setDefaultTargetUrl("/food/list"); // 로그인 성공 후 리다이렉트할 URL

		// 리다이렉트 처리
		super.onAuthenticationSuccess(request, response, authentication);
	}

	private int birthdayCoupon(String memberId) {
		Calendar currentCalendar = Calendar.getInstance();
		Calendar birthdayCalendar = Calendar.getInstance();

		Date currentDate = new Date();
		Date birthday = memberService.getMember(memberId).getBirthday();

		currentCalendar.setTime(currentDate);
		birthdayCalendar.setTime(birthday);

		int currentMonth = currentCalendar.get(Calendar.MONTH);
		int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
		int birthdayMonth = birthdayCalendar.get(Calendar.MONTH);
		int birthdayDay = birthdayCalendar.get(Calendar.DAY_OF_MONTH);

		int isExisting = giftCardService.birthdayGiftCardDupCheck(memberId);

		if (currentMonth == birthdayMonth && currentDay == birthdayDay && isExisting != 1) {
			GiftCardVO giftCardVO = new GiftCardVO();
			giftCardVO.setGiftCardName("생일 축하 기프트카드");
			giftCardVO.setBalance(10000);
			giftCardVO.setFoodType("전체");
			giftCardVO.setMemberId(memberId);

			Calendar expirationCalendar = Calendar.getInstance();
			expirationCalendar.setTime(currentDate);
			expirationCalendar.add(Calendar.MONTH, 1);
			Date oneMonthLater = expirationCalendar.getTime();
			giftCardVO.setGiftCardExpiredDate(oneMonthLater);

			return giftCardService.grantGiftCard(giftCardVO);
		}
		return 0;
	}
}
