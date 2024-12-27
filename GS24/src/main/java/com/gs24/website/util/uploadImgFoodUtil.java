package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.FoodVO;
import com.gs24.website.domain.ReviewVO;
import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
	  public static String subStrName(String fileName) {
		  String normalizeName = FilenameUtils.normalize(fileName);
	        int dotIndex = normalizeName.lastIndexOf('.');

	        String realName = normalizeName.substring(0, dotIndex);
	        return realName;
	    }
	  public static String subStrExtension(String fileName) {
		  int dotIndex = fileName.lastIndexOf('.');
		  
		  String extension = fileName.substring(dotIndex + 1);

	        return extension;
	  }
	  
	  public static String makeDir(Object obj) {
	    	String dirPath = "";
	    	if(obj instanceof FoodVO) {
	    		dirPath = "ImgFood\\";
	    	} else if (obj instanceof ReviewVO) {
	    		dirPath = "ImgReview\\";
	    	}
	    	return dirPath;
	    }
	  
	  public static void saveFile(Object obj, String uploadPath, MultipartFile file, String chgName) {
	       
	       
	        File realUploadPath = new File(uploadPath,makeDir(obj));
	        if (!realUploadPath.exists()) {
	            realUploadPath.mkdirs();
	            log.info(realUploadPath.getPath() + " successfully created.");
	        } else {
	            log.info(realUploadPath.getPath() + " already exists.");
	        }

	        File saveFile = new File(realUploadPath, chgName);
	        
	        try {
	            file.transferTo(saveFile);
	            log.info("file upload scuccess");
	        } catch (IllegalStateException e) {
	            log.error(e.getMessage());
	        } catch (IOException e) {
	            log.error(e.getMessage());
	        }

	    }
	  public static void deleteFile(Object obj, String uploadPath,String chgName) {
	        String fullPath = uploadPath + File.separator + makeDir(obj) + chgName;
	        File file = new File(fullPath);
	        if(file.exists()) {
	            if(file.delete()) {
	                System.out.println(fullPath + " file delete success.");
	            } else {
	                System.out.println(fullPath + " file delete failed.");
	            }
	        } else {
	            System.out.println(fullPath + " file not found.");
	        }
	    }
	 // ���� �߻� ���� ������Ʈ�� �ϴµ� Ȯ���ڰ� �޶� ������ �����Ǵ°� �ƴ϶� �ٽ� �����Ǵ� ����� �ʷ��Ǿ���. �� �κ��� �����ؾ� �Ѵ�.
	  // �̸��� �����ϱ� �����Ǿ��� �ش� ���� ������ �� ������ ���� Ȯ���ڵ��� �޾ƿͼ� ���� Ȯ���� �� �����ϰ� ���� Ȯ���ڷ� �����ϴ� ������� ����
	  public static void updateFile(Object obj, String uploadPath,MultipartFile file ,String chgName, String prevExtension, String nextExtention) {
		  deleteFile(obj, uploadPath, chgName + "." + prevExtension);
		  
		  saveFile(obj, uploadPath, file, chgName + "." + nextExtention);
	  }
	}