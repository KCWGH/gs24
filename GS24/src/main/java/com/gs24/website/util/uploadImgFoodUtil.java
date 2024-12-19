package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
<<<<<<< HEAD

    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() :       ̸      ȭ  ޼   
=======
	
	/**
     * ���� �̸����� Ȯ���ڸ� ������ ���� ���� �̸��� ����
     * 
     * @param fileName ���� �̸�
     * @return ���� ���� �̸�
     */
    public static String subStrName(String fileName) {
    	// FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
<<<<<<< HEAD

=======
    /**
     * ���� �̸����� Ȯ���ڸ� ����
     * 
     * @param fileName ���� �̸�
     * @return Ȯ����
     */
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
    public static String subStrExtension(String fileName) {

        int dotIndex = fileName.lastIndexOf('.');

<<<<<<< HEAD
=======
        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    public static String makeDir() {
<<<<<<< HEAD

    	return "ImgFood\\";
    }
    
    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
       
       boolean hasFile = false;
       
=======
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
    	
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
        	log.info("������ �����ϴ�.");
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        } else {
        	hasFile = true;
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
<<<<<<< HEAD

=======
    
    /**
     * ������ ����
     * 
     * @param uploadPath ���� ���ε� ���
     * @param path ������ ����� ��¥ ���
     * @param chgName ����� ���� �̸�
     */
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
    public static void deleteFile(String uploadPath,String chgName) {
    	
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
          
        File file = new File(fullPath);
        
<<<<<<< HEAD
=======
        // ������ �����ϴ��� Ȯ���ϰ� ����
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
