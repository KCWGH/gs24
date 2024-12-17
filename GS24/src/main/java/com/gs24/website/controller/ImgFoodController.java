package com.gs24.website.controller;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
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
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import com.gs24.website.domain.ImgFoodVO;
=======
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import com.gs24.website.domain.ImgFoodVO;
import com.gs24.website.service.ImgFoodService;
>>>>>>> Stashed changes
=======
import com.gs24.website.domain.ImgFoodVO;
>>>>>>> Stashed changes
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
import com.gs24.website.util.uploadImgFoodUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value = "/imgfood")
@Log4j
public class ImgFoodController {
<<<<<<< HEAD

=======
<<<<<<< Updated upstream
	
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
	@Autowired
	private String uploadPath;

	@GetMapping("/register")
	public void registerGET() {
		log.info("registerGET");
	}

	@PostMapping("/register")
	public void registerPOST(ImgFoodVO imgFoodVO) {
		log.info("registerPOST() ����");
		MultipartFile file = imgFoodVO.getFile();
		log.info("���� �̸� : " + file.getOriginalFilename());
		log.info("���� ũ�� : " + file.getSize());

		// �̰� DB�� �����Ǵ� foodId �̰ɷ� �����ϸ鼭 �ϸ� �ɵ�
		String chgName = "7����ǰ";
		// ���� ����
		boolean a = uploadImgFoodUtil.saveFile(uploadPath, file,
				chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
		// ���� ���� �̸� ����
		imgFoodVO.setImgFoodChgName(chgName);
		// ���� Ȯ���� ����
		imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
		// ���� ��� ����
		imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "."
				+ uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

		if (a) {
			// ������ ������ insert
			log.info("������ �����Ͽ� update");
		} else {
			// ������ ������ update
			log.info("������ �������� �ʾ� insert");
		}

		log.info(imgFoodVO);

		// uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
		// uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
	}
<<<<<<< HEAD

=======
	
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
      log.info("      ̸  : " + file.getOriginalFilename());
      log.info("     ũ   : " + file.getSize());

      //  ̰  DB        Ǵ  foodId  ̰ɷ       ϸ鼭  ϸ   ɵ 
      String chgName = "7    ǰ";
      //          
      boolean a = uploadImgFoodUtil.saveFile(uploadPath, file,
            chgName + "." + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      //            ̸      
      imgFoodVO.setImgFoodRealName(uploadImgFoodUtil.subStrName(file.getOriginalFilename()));
      //            ̸      
      imgFoodVO.setImgFoodChgName(chgName);
      //      Ȯ         
     // imgFoodVO.setImgFoodExtension(uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
      //              
      imgFoodVO.setImgFoodPath(uploadPath + uploadImgFoodUtil.makeDir() + chgName + "."
            + uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));

      if (a) {
         //               insert
         log.info("            Ͽ  update");
      } else {
         //               update
         log.info("                 ʾ  insert");
      }

      log.info(imgFoodVO);

      // uploadImgFoodUtil.deleteFile(uploadPath, chgName +"."+
      // uploadImgFoodUtil.subStrExtension(file.getOriginalFilename()));
   }

>>>>>>> Stashed changes
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
}
