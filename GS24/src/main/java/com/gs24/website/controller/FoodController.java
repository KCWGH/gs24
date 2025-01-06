package com.gs24.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.MemberService;
import com.gs24.website.util.PageMaker;
import com.gs24.website.util.Pagination;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private FoodService foodService;

    // 음식 목록 페이지
    @GetMapping("/list")
    public void listGET(HttpSession session, Model model, Pagination pagination) {
        log.info("listGET()");
        String memberId = (String) session.getAttribute("memberId");
        if (memberId != null) {
            MemberVO memberVO = memberService.getMember(memberId);
            model.addAttribute("memberVO", memberVO);
        }
        List<FoodVO> foodList = foodService.getPaginationFood(pagination);
        
        PageMaker pageMaker = new PageMaker();
        pageMaker.setPagination(pagination);
        pageMaker.setTotalCount(foodService.getFoodTotalCount(pagination));
        
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("FoodList", foodList);
    }

    // 음식 등록 페이지
    @GetMapping("/register")
    public void registerGET() {
        log.info("registerGET()");
    }

    // 음식 등록 처리
    @PostMapping("/register")
    public String registerPOST(FoodVO foodVO, MultipartFile file) {
        log.info("registerPOST()");
        log.info(foodVO);
        log.info(file.getOriginalFilename());
        foodService.createFood(foodVO, file);

        return "redirect:/food/list";
    }

    // 음식 상세 페이지
    @GetMapping("/detail")
    public void detailGET(Model model, Integer foodId) {
        log.info("detailGET()");
        FoodVO foodVO = foodService.getFoodById(foodId);
        model.addAttribute("FoodVO", foodVO);
    }

    // 음식 수정 페이지
    @GetMapping("/update")
    public void updateGET(Model model, Integer foodId) {
        log.info("updateGET()");
        FoodVO foodVO = foodService.getFoodById(foodId);
        model.addAttribute("FoodVO", foodVO);
    }

    // 음식 수정 처리
    @PostMapping("/update")
    public String updatePOST(FoodVO foodVO, MultipartFile file) {
        log.info("updatePOST()");
        // 음식 정보 수정
        int result = foodService.updateFood(foodVO, file);
        if (result > 0) {
            log.info("음식 정보 업데이트 성공");
        } else {
            log.info("음식 정보 업데이트 실패");
        }

		return "redirect:/food/list";
	}

	@GetMapping("/delete")
	public String delete(int foodId) {
		log.info("deleteFood()");
		foodService.deleteFood(foodId);

		return "redirect:/food/list";
	}
}
