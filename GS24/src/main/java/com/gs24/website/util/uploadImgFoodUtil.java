package com.gs24.website.util;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

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
<<<<<<< HEAD
    	// FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
=======
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
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
<<<<<<< HEAD
     * ���� �̸����� Ȯ���ڸ� ����
=======
<<<<<<< Updated upstream
     * ���� �̸����� Ȯ���ڸ� ����
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
     * 
     * @param fileName ���� �̸�
     * @return Ȯ����
     */
    public static String subStrExtension(String fileName) {
        // ���� �̸����� ������ '.'�� �ε����� ã���ϴ�.
        int dotIndex = fileName.lastIndexOf('.');

<<<<<<< HEAD
        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
=======
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
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
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
<<<<<<< HEAD
        	log.info("������ �����ϴ�.");
=======
<<<<<<< Updated upstream
        	log.info("������ �����ϴ�.");
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
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
<<<<<<< HEAD
     * ������ ����
=======
<<<<<<< Updated upstream
     * ������ ����
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
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
        
<<<<<<< HEAD
        // ������ �����ϴ��� Ȯ���ϰ� ����
=======
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
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
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
