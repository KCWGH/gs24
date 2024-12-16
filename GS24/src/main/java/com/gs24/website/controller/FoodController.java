package com.gs24.website.controller;

<<<<<<< Updated upstream
import javax.servlet.http.HttpSession;
=======
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.List;
>>>>>>> Stashed changes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

<<<<<<< Updated upstream
@Controller // @Component
@RequestMapping(value = "/food")
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.util.uploadImgFoodUtil;

@Controller
@RequestMapping("/food")
>>>>>>> Stashed changes
@Log4j
public class FoodController {

	@Autowired
<<<<<<< Updated upstream
	private MemberMapper memberMapper;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberMapper.select(memberId);
			model.addAttribute("memberVO", memberVO);
		}
	}


} // end BoardController
=======
	private FoodService foodService;
	@Autowired
	private ImgFoodService imgFoodService;
	@Autowired
	private String uploadPath;

	@GetMapping("/list")
	public void list(Model model) {
		log.info("list() 실행");
		List<FoodVO> FoodList = foodService.getAllFood();
		log.info(FoodList);
		model.addAttribute("FoodList", FoodList);
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET() 실행");
	}

	@PostMapping("/register")
	// 여기에 multipartfile file이 온다고 하면
	public String registerPOST(FoodVO foodVO, MultipartFile file) {
		log.info("registerPOST() 실행");
		log.info(foodVO);
		log.info(file.getOriginalFilename());
		foodService.CreateFood(foodVO);

		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("파일 이름 : " + file.getOriginalFilename());
		log.info("파일 크기 : " + file.getSize());
		
		FoodVO VO = foodService.getFirstFoodId();
		
		// 이건 DB에 저정되는 foodId 이걸로 변경하면서 하면 될듯
		String chgName = "FoodNO"+ VO.getFoodId();
		// 파일 저장
		boolean hasFile = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// 파일 실제 이름 설정
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// 파일 변경 이름 설정
		imgFoodVO.setImgFoodChgName(chgName);
		// 파일 확장자 설정
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// 파일 경로 설정
		imgFoodVO.setImgFoodPath(uploadPath + File.separator + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// foodId 설정
		imgFoodVO.setFoodId(VO.getFoodId());
		if (hasFile) {
			// 파일이 있으면 update
			log.info("파일이 이미 존재합니다.");
			imgFoodService.updateImgFood(imgFoodVO);
		} else {
			// 파일이 없으면 insert
			log.info("파일이 존재하지 않습니다.");
			imgFoodService.createImgFood(imgFoodVO);
		}

		log.info(imgFoodVO);

		return "redirect:/food/list";
	}
	
	
	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId) {
		log.info("detailGET() 실행");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		// 사진 경로가 http:// 이거야 한다는 거지? 근데 이렇게 GET방식으로 하면 아예 다른페이지로 가서 출력하는거 아닌가? 
		// TODO 비동기 방식으로 이미지를 불러와야 내가 원하는 방향과 맞는 건가
		// <img alt="" src="<%=request.getContextPath()%>/attach/showImg.wow?fileName=${f.atchFileName}&filePath=${f.atchPath}" width="50px" height="50px">
		
	}

	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId) {
		log.info("updateGET() 실행");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO, MultipartFile file) {
		log.info("updatePOST() 실행");
		int result = foodService.updateFood(foodVO);
		
		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("파일 이름 : " + file.getOriginalFilename());
		log.info("파일 크기 : " + file.getSize());
		
		// 이건 DB에 저정되는 foodId 이걸로 변경하면서 하면 될듯
		String chgName = foodVO.getFoodId() + "번 식품";
		// 파일 저장
		boolean hasFile = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// 파일 실제 이름 설정
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// 파일 변경 이름 설정
		imgFoodVO.setImgFoodChgName(chgName);
		// 파일 확장자 설정
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// 파일 경로 설정
		imgFoodVO.setImgFoodPath(uploadPath + File.separator + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// foodId 설정
		imgFoodVO.setFoodId(foodVO.getFoodId());
		if (hasFile) {
			// 파일이 있으면 update
			log.info("파일이 존재하여 update");
			imgFoodService.updateImgFood(imgFoodVO);
		} else {
			// 파일이 없으면 insert
			log.info("파일이 존재하지 않아 insert");
			imgFoodService.createImgFood(imgFoodVO);
		}

		log.info(imgFoodVO);
		
		return "redirect:/food/list";
	}
	
	@GetMapping("/delete")
	public String delete(int foodId) {
		
		foodService.deleteFood(foodId);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		imgFoodService.deleteImgFood(foodId);
		uploadImgFoodUtil.deleteFile(uploadPath, imgFoodVO.getImgFoodChgName() + "." + imgFoodVO.getImgFoodExtension());
		
		return "redirect:/food/list";
	}
}
>>>>>>> Stashed changes
