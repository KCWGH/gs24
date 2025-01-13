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

    // �쓬�떇 紐⑸줉 �럹�씠吏�
    @GetMapping("/list")
    public void listGET(HttpSession session, Model model, Pagination pagination) {
        log.info("listGET()");
        log.info("pagination" + pagination);
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

    // �쓬�떇 �벑濡� �럹�씠吏�
    @GetMapping("/register")
    public void registerGET() {
        log.info("registerGET()");
    }

    // �쓬�떇 �벑濡� 泥섎━
    @PostMapping("/register")
    public String registerPOST(FoodVO foodVO, MultipartFile file) {
        log.info("registerPOST()");
        log.info(foodVO);
        log.info(file.getOriginalFilename());
        foodService.createFood(foodVO, file);

        return "redirect:/food/list";
    }

    // �쓬�떇 �긽�꽭 �럹�씠吏�
    @GetMapping("/detail")
    public void detailGET(Model model, Integer foodId) {
        log.info("detailGET()");
        Object[] detailData = foodService.getDetailData(foodId);
        log.info(detailData[1]);
        model.addAttribute("FoodVO", detailData[0]);
        model.addAttribute("reviewList", detailData[1]);
    }

    // �쓬�떇 �닔�젙 �럹�씠吏�
    @GetMapping("/update")
    public void updateGET(Model model, Integer foodId) {
        log.info("updateGET()");
        FoodVO foodVO = foodService.getFoodById(foodId);
        model.addAttribute("FoodVO", foodVO);
    }

    // �쓬�떇 �닔�젙 泥섎━
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
