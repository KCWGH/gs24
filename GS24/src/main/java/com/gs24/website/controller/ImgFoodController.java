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
		log.info("registerPOST() ï¿½ï¿½ï¿½ï¿½");
		log.info(file.getOriginalFilename());
		String realName = uploadImgFoodUtil.RealName(file.getOriginalFilename());
		log.info(realName);
=======
	public void registerPOST(ImgFoodVO imgFoodVO) {
		log.info("registerPOST() ½ÇÇà");
		MultipartFile file = imgFoodVO.getFile();
		log.info("ÆÄÀÏ ÀÌ¸§ : " + file.getOriginalFilename());
		log.info("ÆÄÀÏ Å©±â : " + file.getSize());
>>>>>>> Stashed changes
		
		// ÀÌ°Ç DB¿¡ ÀúÁ¤µÇ´Â foodId ÀÌ°É·Î º¯°æÇÏ¸é¼­ ÇÏ¸é µÉµí
	      String chgName = "7¹ø½ÄÇ°";
	      // ÆÄÀÏ ÀúÀå
	      boolean a = uploadImgFoodUtil.saveFile(uploadPath, file, chgName +"."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

	      // ÆÄÀÏ ½ÇÁ¦ ÀÌ¸§ ¼³Á¤
	      imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
	      // ÆÄÀÏ º¯°æ ÀÌ¸§ ¼³Á¤
	      imgFoodVO.setImgFoodChgName(chgName);
	      // ÆÄÀÏ È®ÀåÀÚ ¼³Á¤
	      imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	      // ÆÄÀÏ °æ·Î ¼³Á¤
	      imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName +"."+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	      
	      if(a) {
	    	  // ÆÄÀÏÀÌ ¾øÀ¸¸é insert
	    	  log.info("ÆÄÀÏÀÌ Á¸ÀçÇÏ¿© update");
	      }
	      else {
	    	// ÆÄÀÏÀÌ ÀÖÀ¸¸é update
	    	  log.info("ÆÄÀÏÀÌ Á¸ÀçÇÏÁö ¾Ê¾Æ insert");
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
      log.info("      Ì¸  : " + file.getOriginalFilename());
      log.info("     Å©   : " + file.getSize());

      //  Ì°  DB        Ç´  foodId  Ì°É·       Ï¸é¼­  Ï¸   Éµ 
      String chgName = "7    Ç°";
      //          
      boolean a = uploadImgFoodUtil.saveFile(uploadPath, file,
            chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      //            Ì¸      
      imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
      //            Ì¸      
      imgFoodVO.setImgFoodChgName(chgName);
      //      È®         
     // imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
      //              
      imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "."
            + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      if (a) {
         //               insert
         log.info("            Ï¿  update");
      } else {
         //               update
         log.info("                 Ê¾  insert");
      }

      log.info(imgFoodVO);

      // uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
      // uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
   }

>>>>>>> Stashed changes
}
