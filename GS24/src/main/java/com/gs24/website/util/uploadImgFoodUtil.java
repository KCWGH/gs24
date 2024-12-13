package com.gs24.website.util;

import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
	
	public static String RealName(String filePath) {
//		private int ImgFoodId; 얘는 DB 저장할 때 알아서 되는거
//		private int foodId; 얘는 여기서 초기화할 필요없이 외부에서 하면 되고
//		private String ImgFoodPath;
//		private String ImgFoodRealName; 이건 했고
//		private String ImgFoodChgName;
//		private String ImgFoodExtention; 이건 했고
//		private Date ImgFoodDateCreated; 얘도 DB에 저장할 때 알아서 되고
//		private MultipartFile file; 얘는 매개변수꺼 그대로 가져오면 되겠고
		
		String nomalizeName = FilenameUtils.normalize(filePath);
		int dotIndex = nomalizeName.lastIndexOf('.');

        String realName = nomalizeName.substring(0, dotIndex);
        return realName;
	}
	
	public static String Extention(String filePath) {
			int dotIndex = filePath.lastIndexOf('.');
	        String extension = filePath.substring(dotIndex + 1);

	        return extension;
	}
}
