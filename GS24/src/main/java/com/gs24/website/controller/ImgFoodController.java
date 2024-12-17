package com.gs24.website.controller;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
=======
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;
>>>>>>> Stashed changes
=======
import com.gs24.website.domain.ImgFoodVO;
>>>>>>> Stashed changes
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/imgfood")
@Log4j
public class ImgFoodController {
<<<<<<< Updated upstream
	
	@Autowired
	private String uploadPath;
	
	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET");
	}
	
	@PostMapping("/register")
<<<<<<< Updated upstream
	public void registerPOST(MultipartFile file) {
		log.info("registerPOST() 占쏙옙占쏙옙");
		log.info(file.getOriginalFilename());
		String realName = uploadImgFoodUtil.RealName(file.getOriginalFilename());
		log.info(realName);
=======
	public void registerPOST(ImgFoodVO imgFoodVO) {
		log.info("registerPOST() 실행");
		MultipartFile file = imgFoodVO.getFile();
		log.info("파일 이름 : " + file.getOriginalFilename());
		log.info("파일 크기 : " + file.getSize());
>>>>>>> Stashed changes
		
		// 이건 DB에 저정되는 foodId 이걸로 변경하면서 하면 될듯
	      String chgName = "7번식품";
	      // 파일 저장
	      boolean a = uploadImgFoodUtil.saveFile(uploadPath, file, chgName +"."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

	      // 파일 실제 이름 설정
	      imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
	      // 파일 변경 이름 설정
	      imgFoodVO.setImgFoodChgName(chgName);
	      // 파일 확장자 설정
	      imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	      // 파일 경로 설정
	      imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName +"."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	      
	      if(a) {
	    	  // 파일이 없으면 insert
	    	  log.info("파일이 존재하여 update");
	      }
	      else {
	    	// 파일이 있으면 update
	    	  log.info("파일이 존재하지 않아 insert");
	      }
	      
	      
	      log.info(imgFoodVO);
	      
	     // uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	}
	
=======

   @Autowired
   private String uploadPath;

   @GetMapping("/register")
   public void registerGET() {
      log.info("registerGET");
   }

   @PostMapping("/register")
   public void registerPOST(ImgFoodVO imgFoodVO) {
      log.info("registerPOST()     ");
      MultipartFile file = imgFoodVO.getFile();
      log.info("      見  : " + file.getOriginalFilename());
      log.info("     크   : " + file.getSize());

      //  隔  DB        풔  foodId  隔��       玖庸�  玖   �� 
      String chgName = "7    품";
      //          
      boolean a = uploadImgFoodUtil.saveFile(uploadPath, file,
            chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      //            見      
      imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
      //            見      
      imgFoodVO.setImgFoodChgName(chgName);
      //      확         
     // imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
      //              
      imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "."
            + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      if (a) {
         //               insert
         log.info("            臼  update");
      } else {
         //               update
         log.info("                 刻  insert");
      }

      log.info(imgFoodVO);

      // uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
      // uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
   }

>>>>>>> Stashed changes
}
