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
     * ���� �̸����� Ȯ���ڸ� ������ ���� ���� �̸��� ����
     * 
     * @param fileName ���� �̸�
     * @return ���� ���� �̸�
     */
    public static String subStrName(String fileName) {
    	// FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
=======
   
   /**
     *       ̸      Ȯ   ڸ                    ̸        
     * 
     * @param fileName       ̸ 
     * @return            ̸ 
     */
    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() :       ̸      ȭ  ޼   
>>>>>>> Stashed changes
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
<<<<<<< Updated upstream
     * ���� �̸����� Ȯ���ڸ� ����
     * 
     * @param fileName ���� �̸�
     * @return Ȯ����
     */
    public static String subStrExtension(String fileName) {
        // ���� �̸����� ������ '.'�� �ε����� ã���ϴ�.
        int dotIndex = fileName.lastIndexOf('.');

        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
=======
     *       ̸      Ȯ   ڸ      
     * 
     * @param fileName       ̸ 
     * @return Ȯ    
     */
    public static String subStrExtension(String fileName) {
        //       ̸             '.'    ε      ã   ϴ .
        int dotIndex = fileName.lastIndexOf('.');

        // '.'           ڿ    Ȯ   ڷ       մϴ .
>>>>>>> Stashed changes
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    public static String makeDir() {
<<<<<<< Updated upstream
    	return "ImgFood\\";
    }
    
    /**
     * ������ ����
     * 
     * @param uploadPath ���� ���ε� ���
     * @param file ���ε�� ����
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
     * @param uploadPath         ε     
     * @param file    ε       
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
        	log.info("������ �����ϴ�.");
        } else {
        	log.info("���� ������ �����մϴ�.");
        	hasFile = true;
=======
           log.info("            ϴ .");
        } else {
           log.info("                 մϴ .");
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
     * ������ ����
     * 
     * @param uploadPath ���� ���ε� ���
     * @param path ������ ����� ��¥ ���
     * @param chgName ����� ���� �̸�
     */
    public static void deleteFile(String uploadPath,String chgName) {
        // ������ ������ ��ü ��� ����
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        // ���� ��ü ����
        File file = new File(fullPath);
        
        // ������ �����ϴ��� Ȯ���ϰ� ����
=======
     *            
     * 
     * @param uploadPath         ε     
     * @param path                ¥    
     * @param chgName             ̸ 
     */
    public static void deleteFile(String uploadPath,String chgName) {
        //                 ü         
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        //        ü     
        File file = new File(fullPath);
        
        //             ϴ    Ȯ   ϰ      
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
