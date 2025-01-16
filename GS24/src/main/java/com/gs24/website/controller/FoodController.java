package com.gs24.website.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.CustomUser;
import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FavoritesService;
import com.gs24.website.service.FoodService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private FavoritesService favoritesService;

	@GetMapping("/list")
	public void listGET(Model model, Pagination pagination) {
		log.info("listGET()");
		log.info("pagination" + pagination);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<FoodVO> foodList = foodService.getPaginationFood(pagination);
		if (principal instanceof CustomUser) {
			CustomUser customUser = (CustomUser) principal;
			MemberVO memberVO = customUser.getMemberVO();
			model.addAttribute("memberId", memberVO.getMemberId());
			Map<Integer, Integer> isAddedMap = new HashMap<>();
			for (FoodVO foodVO : foodList) {
				// 찜 여부 확인: 이미 찜한 음식은 1, 찜하지 않은 음식은 0
				int isAdded = favoritesService.isAddedCheck(memberVO.getMemberId(), foodVO.getFoodId());
				isAddedMap.put(foodVO.getFoodId(), isAdded);
				model.addAttribute("isAddedMap", isAddedMap);
			}
		}

		PageMaker pageMaker = new PageMaker();
		pageMaker.setPagination(pagination);
		pageMaker.setTotalCount(foodService.getFoodTotalCount(pagination));

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("FoodList", foodList);
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET()");
	}

	@PostMapping("/register")
	public String registerPOST(FoodVO foodVO, MultipartFile file) {
		log.info("registerPOST()");
		log.info(foodVO);
		log.info(file.getOriginalFilename());
		foodService.createFood(foodVO, file);

		return "redirect:/food/list";
	}

	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId) {
		log.info("detailGET()");
		Object[] detailData = foodService.getDetailData(foodId);
		log.info(detailData[1]);
		model.addAttribute("FoodVO", detailData[0]);
		model.addAttribute("reviewList", detailData[1]);
	}

	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId) {
		log.info("updateGET()");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO, MultipartFile file) {
		log.info("updatePOST()");
		// �쓬�떇 �젙蹂� �닔�젙
		int result = foodService.updateFood(foodVO, file);
		if (result > 0) {
			log.info("�쓬�떇 �젙蹂� �뾽�뜲�씠�듃 �꽦怨�");
		} else {
			log.info("�쓬�떇 �젙蹂� �뾽�뜲�씠�듃 �떎�뙣");
		}

		return "redirect:/food/list";
	}

	@GetMapping("/delete")
	public String delete(int foodId, int reviewId) {
		log.info("deleteFood()");
		foodService.deleteFood(foodId, reviewId);

		return "redirect:/food/list";
	}
}
