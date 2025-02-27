package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gs24.website.domain.FoodListVO;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.FoodListService;
import com.gs24.website.service.PreorderService;
import com.gs24.website.service.QuestionService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/member")
@Log4j
public class MemberRESTController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private FoodListService foodListService;

	@Autowired
	private FavoritesService favoritesService;

	@GetMapping("/myQuestions")
	public ResponseEntity<Map<String, Object>> questionList(Pagination pagination, Authentication auth) {
		return postListGET("myQuestions", pagination, auth);
	}

	@GetMapping("/myPreorders")
	public ResponseEntity<Map<String, Object>> preorderList(Pagination pagination, Authentication auth) {
		return postListGET("myPreorders", pagination, auth);
	}

	@GetMapping("/myFavorites")
	public ResponseEntity<Map<String, Object>> notificationList(Pagination pagination, Authentication auth) {
		return postListGET("myFavorites", pagination, auth);
	}

	public ResponseEntity<Map<String, Object>> postListGET(String type, Pagination pagination, Authentication auth) {
		log.info("postListGET()");
		String memberId = auth.getName();
		Map<String, Object> response = new HashMap<>();
		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		List<?> postList = null;
		switch (type) {
		case "myQuestions":
			postList = questionService.getPagedQuestionsByMemberId(memberId, pagination);
			pageMaker.setTotalCount(questionService.countQuestionByMemberId(memberId));
			break;
		case "myPreorders":
			postList = preorderService.getPagedPreordersByMemberId(memberId, pagination);
			pageMaker.setTotalCount(preorderService.countPreorderByMemberId(memberId));
			break;
		case "myFavorites":
			postList = favoritesService.getPagedFavoritesByMemberId(memberId, pagination);
			pageMaker.setTotalCount(favoritesService.countFavoritesByMemberId(memberId));
			break;
		}
		response.put("pageMaker", pageMaker);
		response.put("postList", postList);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/get-food-name", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String getFoodName(int foodId) {
		FoodListVO foodVO = foodListService.getFoodById(foodId);
		return foodVO != null ? foodVO.getFoodName() : "식품 정보 없음";
	}

}
