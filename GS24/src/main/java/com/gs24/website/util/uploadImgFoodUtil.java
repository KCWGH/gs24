package com.gs24.website.util;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.ImgFoodVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
<<<<<<< Updated upstream
	
	/**
     * 파일 이름에서 확장자를 제외한 실제 파일 이름을 추출
     * 
     * @param fileName 파일 이름
     * @return 실제 파일 이름
     */
    public static String subStrName(String fileName) {
    	// FilenameUtils.normalize() : 파일 이름 정규화 메서드
=======
   
   /**
     *       見      확   美                    見        
     * 
     * @param fileName       見 
     * @return            見 
     */
    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() :       見      화  氷   
>>>>>>> Stashed changes
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
<<<<<<< Updated upstream
     * 파일 이름에서 확장자를 추출
     * 
     * @param fileName 파일 이름
     * @return 확장자
     */
    public static String subStrExtension(String fileName) {
        // 파일 이름에서 마지막 '.'의 인덱스를 찾습니다.
        int dotIndex = fileName.lastIndexOf('.');

        // '.' 이후의 문자열을 확장자로 추출합니다.
=======
     *       見      확   美      
     * 
     * @param fileName       見 
     * @return 확    
     */
    public static String subStrExtension(String fileName) {
        //       見             '.'    琯      찾   求 .
        int dotIndex = fileName.lastIndexOf('.');

        // '.'           悶    확   米       爛求 .
>>>>>>> Stashed changes
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    public static String makeDir() {
<<<<<<< Updated upstream
    	return "ImgFood\\";
    }
    
    /**
     * 파일을 저장
     * 
     * @param uploadPath 파일 업로드 경로
     * @param file 업로드된 파일
     * @param uuid UUID
     */
    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
    	
    	boolean hasFile = false;
    	
=======
       return "ImgFood\\";
    }
    
    /**
     *            
     * 
     * @param uploadPath         琯     
     * @param file    琯       
     * @param uuid UUID
     */
    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
       
       boolean hasFile = false;
       
>>>>>>> Stashed changes
        File realUploadPath = new File(uploadPath,makeDir());
        if (!realUploadPath.exists()) {
            realUploadPath.mkdirs();
            log.info(realUploadPath.getPath() + " successfully created.");
        } else {
            log.info(realUploadPath.getPath() + " already exists.");
        }
        
        File saveFile = new File(realUploadPath, chgName);
        if(!saveFile.exists()) {
<<<<<<< Updated upstream
        	log.info("파일이 없습니다.");
        } else {
        	log.info("기존 파일이 존재합니다.");
        	hasFile = true;
=======
           log.info("            求 .");
        } else {
           log.info("                 爛求 .");
           hasFile = true;
>>>>>>> Stashed changes
        }
        
        try {
            file.transferTo(saveFile);
            log.info("file upload scuccess");
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        
        return hasFile;
    }
    
    /**
<<<<<<< Updated upstream
     * 파일을 삭제
     * 
     * @param uploadPath 파일 업로드 경로
     * @param path 파일이 저장된 날짜 경로
     * @param chgName 저장된 파일 이름
     */
    public static void deleteFile(String uploadPath,String chgName) {
        // 삭제할 파일의 전체 경로 생성
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        // 파일 객체 생성
        File file = new File(fullPath);
        
        // 파일이 존재하는지 확인하고 삭제
=======
     *            
     * 
     * @param uploadPath         琯     
     * @param path                짜    
     * @param chgName             見 
     */
    public static void deleteFile(String uploadPath,String chgName) {
        //                 체         
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        //        체     
        File file = new File(fullPath);
        
        //             求    확   構      
>>>>>>> Stashed changes
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
}
