package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgUtil {
	
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
	  
	  public static String makeDir(String dir) {
	    	String dirPath = "";
	    	if(dir.startsWith("Food")) {
	    		dirPath = "ImgFood" + File.separator + dir + File.separator;
	    	} else if(dir.startsWith("Review")) {
	    		dirPath = "ImgReview"  + File.separator + dir + File.separator;
	    	}
	    	return dirPath;
	  }
	  
	  public static void saveFile(String uploadPath, MultipartFile file,String dir, String chgName, String extention) {
	       
	       File realUploadPath = new File(uploadPath,makeDir(dir));
	        if (!realUploadPath.exists()) {
	            realUploadPath.mkdirs();
	            log.info(realUploadPath.getPath() + " successfully created.");
	        } else {
	            log.info(realUploadPath.getPath() + " already exists.");
	        }

	        File saveFile = new File(realUploadPath, chgName + "." + extention);
	        
	        try {
	            file.transferTo(saveFile);
	            log.info("file upload scuccess");
	        } catch (IllegalStateException e) {
	            log.error(e.getMessage());
	        } catch (IOException e) {
	            log.error(e.getMessage());
	        }
	  }
	  
	  public static void deleteFile(String uploadPath,String dir,String chgName, String extention) {
	        String fullPath = uploadPath + File.separator + makeDir(dir) + chgName + "." + extention;
	        File file = new File(fullPath);
	        if(file.exists()) {
	        	log.info(fullPath + " found");
	        	System.gc();
	            if(file.delete()) {
	                log.info(fullPath + " delete success.");
	            } else {
	                log.error(fullPath + " delete failed.");
	            }
	        } else {
	            log.error(fullPath + " not found.");
	        }
	    }
	  
	  public static void deleteFile(String fullPath) {
		  File file = new File(fullPath);
		  log.info(file.isFile());
		  if(file.exists()) {
			  log.info(fullPath + " found");
			  System.gc();
			  if(file.delete()) {
				  log.info(fullPath + " delete success");
			  } else {
				  log.error(fullPath + " delete failed");
			  }
		  } else {
			  log.error(fullPath + " not found");
		  }
	  }
}
