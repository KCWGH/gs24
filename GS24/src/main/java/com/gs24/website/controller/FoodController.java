package com.gs24.website.controller;

import java.io.File;
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
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.domain.MemberVO;
import com.gs24.website.service.FoodService;
import com.gs24.website.service.ImgFoodService;
import com.gs24.website.service.MemberService;
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/food")
@Log4j
public class FoodController {

	@Autowired
	private MemberService memberMapper;
	@Autowired
	private FoodService foodService;
	@Autowired
	private ImgFoodService imgFoodService;
	@Autowired
	private String uploadPath;

	// register.jsp
	@GetMapping("/list")
	public void listGET(HttpSession session, Model model) {
		log.info("listGET()");
		String memberId = (String) session.getAttribute("memberId");
		if (memberId != null) {
			MemberVO memberVO = memberMapper.getMember(memberId);
			model.addAttribute("memberVO", memberVO);
		}
		List<FoodVO> FoodList = foodService.getAllFood();
		model.addAttribute("FoodList", FoodList);
	}

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET() ����");
	}

	@PostMapping("/register")
	// ���⿡ multipartfile file�� �´ٰ� �ϸ�
	public String registerPOST(FoodVO foodVO, MultipartFile file) {
		log.info("registerPOST() ����");
		log.info(foodVO);
		log.info(file.getOriginalFilename());
		foodService.CreateFood(foodVO);

		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("���� �̸� : " + file.getOriginalFilename());
		log.info("���� ũ�� : " + file.getSize());

		FoodVO VO = foodService.getFirstFoodId();

		// �̰� DB�� �����Ǵ� foodId �̰ɷ� �����ϸ鼭 �ϸ� �ɵ�
		String chgName = "FoodNO" + VO.getFoodId();
		// ���� ����
		boolean hasFile = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodChgName(chgName);
		// ���� Ȯ���� ����
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// ���� ��� ����
		imgFoodVO.setImgFoodPath(uploadPath + File.separator + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// foodId ����
		imgFoodVO.setFoodId(VO.getFoodId());
		if (hasFile) {
			// ������ ������ update
			log.info("������ �̹� �����մϴ�.");
			imgFoodService.updateImgFood(imgFoodVO);
		} else {
			// ������ ������ insert
			log.info("������ �������� �ʽ��ϴ�.");
			imgFoodService.createImgFood(imgFoodVO);
		}

		log.info(imgFoodVO);

		return "redirect:/food/list";
	}

	@GetMapping("/detail")
	public void detailGET(Model model, Integer foodId) {
		log.info("detailGET() ����");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
		ImgFoodVO imgFoodVO = imgFoodService.getImgFoodById(foodId);
		// ���� ��ΰ� http:// �̰ž� �Ѵٴ� ����? �ٵ� �̷��� GET������� �ϸ� �ƿ� �ٸ���������
		// ���� ����ϴ°� �ƴѰ�?
		// TODO �񵿱� ������� �̹����� �ҷ��;� ���� ���ϴ� ����� �´� �ǰ�
		// <img alt=""
		// src="<%=request.getContextPath()%>/attach/showImg.wow?fileName=${f.atchFileName}&filePath=${f.atchPath}"
		// width="50px" height="50px">

	}

	@GetMapping("/update")
	public void updateGET(Model model, Integer foodId) {
		log.info("updateGET() ����");
		FoodVO foodVO = foodService.getFoodById(foodId);
		model.addAttribute("FoodVO", foodVO);
	}

	@PostMapping("/update")
	public String updatePOST(FoodVO foodVO, MultipartFile file) {
		log.info("updatePOST() ����");
		int result = foodService.updateFood(foodVO);

		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("���� �̸� : " + file.getOriginalFilename());
		log.info("���� ũ�� : " + file.getSize());

		// �̰� DB�� �����Ǵ� foodId �̰ɷ� �����ϸ鼭 �ϸ� �ɵ�
		String chgName = foodVO.getFoodId() + "�� ��ǰ";
		// ���� ����
		boolean hasFile = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodChgName(chgName);
		// ���� Ȯ���� ����
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// ���� ��� ����
		imgFoodVO.setImgFoodPath(uploadPath + File.separator + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// foodId ����
		imgFoodVO.setFoodId(foodVO.getFoodId());
		if (hasFile) {
			// ������ ������ update
			log.info("������ �����Ͽ� update");
			imgFoodService.updateImgFood(imgFoodVO);
		} else {
			// ������ ������ insert
			log.info("������ �������� �ʾ� insert");
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