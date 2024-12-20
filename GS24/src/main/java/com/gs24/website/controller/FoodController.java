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

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private FoodService foodService;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		//TODO 페이징처리 해야해 꼭!!!!!
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberService.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		List<FoodVO> FoodList = foodService.getAllFood();
		model.addAttribute("FoodList", FoodList);
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
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);

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
<<<<<<< Updated upstream
		int result = foodService.updateFood(foodVO);

		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("file name : " + file.getOriginalFilename());
		log.info("file size : " + file.getSize());

		String chgName = "FoodNO" + foodVO.getFoodId();

		boolean hasFile = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));

		imgFoodVO.setImgFoodChgName(chgName);

		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setImgFoodPath(uploadPath + File.separator + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setFoodId(foodVO.getFoodId());
		if (hasFile) {
			log.info("Successed update image");
			imgFoodService.updateImgFood(imgFoodVO);
		} else {
			log.info("Failed update image");
		}

		log.info(imgFoodVO);
=======
		int result = foodService.updateFood(foodVO, file);
>>>>>>> Stashed changes

		return "redirect:/food/list";
	}

	@GetMapping("/delete")
	public String delete(int foodId) {
		log.info("deleteFood()");
		foodService.deleteFood(foodId);

		return "redirect:/food/list";
	}
}