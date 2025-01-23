package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.MemberService;
import com.gs24.website.service.PreorderService;
import com.gs24.website.service.QuestionService;
import com.gs24.website.service.ReviewService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/member")
@Log4j
public class MemberRESTController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private FoodService foodService;

	@Autowired
	private FavoritesService favoritesService;

	@PostMapping("/update-email")
	public ResponseEntity<String> updateEmailPOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getEmail().equals(memberService.findEmailById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Email");
		} else {
			int dupCheck = memberService.dupCheckEmail(memberVO.getEmail());
			if (dupCheck == 0) {
				int result = memberService.updateMemberEmail(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("이메일 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Email");
			}
		}

	}

	@PostMapping("/update-phone")
	public ResponseEntity<String> updatePhonePOST(@RequestBody MemberVO memberVO) {
		if (memberVO.getPhone().equals(memberService.findPhoneById(memberVO.getMemberId()))) {
			return ResponseEntity.ok("Update Fail - Same Phone");
		} else {
			int dupCheck = memberService.dupCheckPhone(memberVO.getPhone());
			if (dupCheck == 0) {
				int result = memberService.updateMemberPhone(memberVO);
				if (result == 0) { // 업데이트가 안 되면
					log.info("휴대폰 번호 수정 실패");
					return ResponseEntity.ok("Update Fail");
				} else {
					log.info("updateEmailPOST()");
					return ResponseEntity.ok("Update Success");
				}
			} else {
				return ResponseEntity.ok("Update Fail - Duplicated Phone");
			}
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deletePOST(String memberId, HttpServletRequest request,
			HttpServletResponse response) {
		int checkPreorders = preorderService.countRemainingPreorders(memberId);
		if (checkPreorders == 0) {
			int result = memberService.deleteMember(memberId);
			if (result == 1) {
				new SecurityContextLogoutHandler().logout(request, response,
						SecurityContextHolder.getContext().getAuthentication());
				request.getSession().invalidate();
				return ResponseEntity.ok("Delete Success");
			} else if (result == 2) {
				return ResponseEntity.ok("Delete Fail - Remaining Giftcard Balances");
			} else {
				return ResponseEntity.ok("Delete Fail");
			}
		}
		return ResponseEntity.ok("Delete Fail - Remaining Preorders");
	}

	@GetMapping("/myReviews")
	public ResponseEntity<Map<String, Object>> reviewList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return postListGET("myReviews", pagination, customUser);
	}

	@GetMapping("/myQuestions")
	public ResponseEntity<Map<String, Object>> questionList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return postListGET("myQuestions", pagination, customUser);
	}

	@GetMapping("/myPreorders")
	public ResponseEntity<Map<String, Object>> preorderList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return postListGET("myPreorders", pagination, customUser);
	}

	@GetMapping("/myFavorites")
	public ResponseEntity<Map<String, Object>> notificationList(Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		return postListGET("myFavorites", pagination, customUser);
	}

	public ResponseEntity<Map<String, Object>> postListGET(String type, Pagination pagination,
			@AuthenticationPrincipal CustomUser customUser) {
		log.info("postListGET()");
		String memberId = customUser.getUsername();
		Map<String, Object> response = new HashMap<>();
		MemberVO memberVO = memberService.getMember(memberId);
		memberVO.setPassword(null);
		memberVO.setPhone(null);
		memberVO.setEmail(null);
		memberVO.setBirthday(null);
		pagination.setMemberVO(memberVO);
		pagination.setPageSize(5);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		List<?> postList = null;

		switch (type) {
		case "myReviews":
			postList = reviewService.getPagedReviewsByMemberId(memberId, pagination);
			pageMaker.setTotalCount(reviewService.countReviewByMemberId(memberId));
			break;
		case "myQuestions":
			postList = questionService.getPagedQuestionsByMemberId(memberId, pagination);
			pageMaker.setTotalCount(questionService.countQuestionByMemberId(memberId));
			break;
		case "myPreorders":
			postList = preorderService.getPagedPreordersByMemberId(memberId, pagination);
			pageMaker.setTotalCount(preorderService.countPreorderByMemberId(memberId));
			break;
		case "myFavorites":
			pagination.setPageSize(4);
			postList = favoritesService.getPagedfavoritesByMemberId(memberId, pagination);
			System.out.println(favoritesService.countFavoritesByMemberId(memberId));
			pageMaker.setTotalCount(favoritesService.countFavoritesByMemberId(memberId));
			break;
		}
		response.put("pageMaker", pageMaker);
		response.put("postList", postList);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/get-food-name", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String getFoodName(int foodId) {
		FoodVO foodVO = foodService.getFoodById(foodId);
		return foodVO != null ? foodVO.getFoodName() : "음식 정보 없음";
	}

}
