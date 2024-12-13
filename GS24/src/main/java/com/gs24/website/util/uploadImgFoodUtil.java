package com.gs24.website.util;

import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
	
	public static String RealName(String filePath) {
//		private int ImgFoodId; ��� DB ������ �� �˾Ƽ� �Ǵ°�
//		private int foodId; ��� ���⼭ �ʱ�ȭ�� �ʿ���� �ܺο��� �ϸ� �ǰ�
//		private String ImgFoodPath;
//		private String ImgFoodRealName; �̰� �߰�
//		private String ImgFoodChgName;
//		private String ImgFoodExtention; �̰� �߰�
//		private Date ImgFoodDateCreated; �굵 DB�� ������ �� �˾Ƽ� �ǰ�
//		private MultipartFile file; ��� �Ű������� �״�� �������� �ǰڰ�
		
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
