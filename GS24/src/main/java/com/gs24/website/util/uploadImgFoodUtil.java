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
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        String extension = fileName.substring(dotIndex + 1);
        return extension;
    }
    
<<<<<<< Updated upstream
    public static String makeDir(Object obj) {
    	String dirPath = "";
    	if(obj instanceof FoodVO) {
    		log.info("FoodVO에요");
    		dirPath = "ImgFood\\";
    	} else if (obj instanceof ReviewVO) {
    		dirPath = "ImgReview\\";
    	}
    	return dirPath;
    }
    
    public static boolean saveFile(Object obj, String uploadPath, MultipartFile file, String chgName) {
       
       boolean hasFile = false;
       
        File realUploadPath = new File(uploadPath,makeDir(obj));
=======
    public static String makeDir() {
        return "ImgFood\\";
    }

    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
        boolean hasFile = false;

        File realUploadPath = new File(uploadPath, makeDir());
>>>>>>> Stashed changes
        if (!realUploadPath.exists()) {
            realUploadPath.mkdirs();
            log.info(realUploadPath.getPath() + " successfully created.");
        } else {
            log.info(realUploadPath.getPath() + " already exists.");
        }

        File saveFile = new File(realUploadPath, chgName);
<<<<<<< Updated upstream
        if(!saveFile.exists()) { 	
        	log.info("파일이 없습니다.");
        } else {
        	hasFile = true;
=======
        if (!saveFile.exists()) {
            log.info("File will be uploaded.");
        } else {
            log.info("File already exists.");
            hasFile = true;
>>>>>>> Stashed changes
        }

        try {
            file.transferTo(saveFile);
            log.info("File upload success");
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return hasFile;
    }

<<<<<<< Updated upstream
    public static void deleteFile(Object obj, String uploadPath,String chgName) {
        String fullPath = uploadPath + File.separator + makeDir(obj) + chgName;
        

        File file = new File(fullPath);
        
        if(file.exists()) {
            if(file.delete()) {
                System.out.println(fullPath + " file delete success.");
=======
    public static void deleteFile(String uploadPath, String chgName) {
        String fullPath = uploadPath + File.separator + makeDir() + chgName;

        File file = new File(fullPath);

        if (file.exists()) {
            if (file.delete()) {
                log.info(fullPath + " file delete success.");
>>>>>>> Stashed changes
            } else {
                log.info(fullPath + " file delete failed.");
            }
        } else {
            log.info(fullPath + " file not found.");
        }
    }
}
