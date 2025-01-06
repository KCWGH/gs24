package com.gs24.website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.persistence.FoodMapper;
import com.gs24.website.persistence.ImgFoodMapper;
import com.gs24.website.util.Pagination;
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class FoodServiceImple implements FoodService{

	@Autowired
	private FoodMapper foodMapper;
	
	@Autowired
	private ImgFoodMapper imgFoodMapper;
	
	@Autowired
	private String uploadPath;
	
	@Override
	@Transactional("transactionManager()")
	public int createFood(FoodVO foodVO, MultipartFile file) {
		log.info("createFood()");
		int result = foodMapper.insertFood(foodVO);
		
		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("file name : " + file.getOriginalFilename());
		log.info("file size : " + file.getSize());

		FoodVO VO = foodMapper.selectFirstFoodId();

		String chgName = "FoodNO" + VO.getFoodId();
		uploadImgFoodUtil.saveFile(foodVO,uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		imgFoodVO.setImgFoodChgName(chgName);
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		imgFoodVO.setImgFoodPath(uploadImgFoodUtil.makeDir(foodVO) + chgName + "."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		imgFoodVO.setFoodId(VO.getFoodId());

		log.info(imgFoodVO); 
		
		return result;
	}

	@Override
	@Transactional("transactionManager()")
	public List<FoodVO> getAllFood() {
		log.info("getAllFood()");
		List<FoodVO> foodList = foodMapper.selectFoodList();
		for(FoodVO i : foodList) {
			ImgFoodVO imgFoodVO = imgFoodMapper.selectImgFoodById(i.getFoodId());
			i.setImgFoodVO(imgFoodVO);
		}
		return foodList;
	}

	@Override
	@Transactional("transactionManager()")
	public FoodVO getFoodById(int foodId) {
		log.info("getFoodById");
		FoodVO foodVO = foodMapper.selectFoodById(foodId);
		foodVO.setImgFoodVO(imgFoodMapper.selectImgFoodById(foodId));
		return foodVO;
	}
	
	public FoodVO getFirstFoodId() {
		log.info("getFirstFoodId()");
		return foodMapper.selectFirstFoodId();
	}
	
	@Override
	@Transactional("transactionManager()")
	public int updateFood(FoodVO foodVO, MultipartFile file) {
		log.info("updateFood()");
		int result = foodMapper.updateFood(foodVO);
		
		ImgFoodVO imgFoodVO = new ImgFoodVO();
		imgFoodVO.setFile(file);
		log.info("file name : " + file.getOriginalFilename());
		log.info("file size : " + file.getSize());

		String chgName = "FoodNO" + foodVO.getFoodId();	
		uploadImgFoodUtil.saveFile(foodVO,uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));


		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));

		imgFoodVO.setImgFoodChgName(chgName);

		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setImgFoodPath(uploadImgFoodUtil.makeDir(foodVO) + chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		imgFoodVO.setFoodId(foodVO.getFoodId());

		log.info(imgFoodVO);
		
		return result;
	}

	@Override
	public int updateFoodStock(int foodId, int foodStock) {
		log.info("updateFoodStock()");
		int result = foodMapper.updateFoodStock(foodId, foodStock);
		return result;
	}

	@Override
	public int updateFoodPrice(int foodId, int foodPrice) {
		log.info("updateFoodPrice()");
		int result = foodMapper.updateFoodPrice(foodId, foodPrice);
		return result;
	}

	@Override
	public int updateFoodProteinFatCarb(int foodId, int foodProtein, int foodFat, int foodCarb) {
		log.info("updateFoodProteinFatCarb()");
		int result = foodMapper.updateFoodProteinFatCarb(foodId, foodProtein, foodFat, foodCarb);
		return result;
	}

	@Override
	public int updateFoodAmountByPreorderAmount(int foodId, int preorderAmount) {
		log.info("updateFoodAmountByPreorderAmount()");
		int result = foodMapper.updateFoodAmountByPreorderAmount(foodId, preorderAmount);
		return result;
	}
	
	@Override
	public int deleteFood(int foodId) {
		log.info("deleteFood()");
		int result = foodMapper.deleteFood(foodId);
		
		ImgFoodVO imgFoodVO = imgFoodMapper.selectImgFoodById(foodId);
		imgFoodMapper.deleteImgFood(foodId);
		uploadImgFoodUtil.deleteFile(new FoodVO(),uploadPath, imgFoodVO.getImgFoodChgName() + "." + imgFoodVO.getImgFoodExtension());
		
		return result;
	}

	@Override
	public String[] getFoodTypeList() {
		return foodMapper.selectFoodType();
	}

	@Override
	public List<FoodVO> getPaginationFood(Pagination pagination) {
		log.info("getPaginationFood()");
		List<FoodVO> list = foodMapper.selectFoodPagination(pagination);
		return list;
	}
	
	@Override
	public int getFoodTotalCount(Pagination pagination) {
		log.info("getFoodTotalCount()");
		int result = foodMapper.selectFoodTotalCount(pagination);
		log.info("FoodTotalCount : " + result);
		return result;
	}
	
}
