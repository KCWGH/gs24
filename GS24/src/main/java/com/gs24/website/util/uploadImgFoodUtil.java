package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
<<<<<<< HEAD
<<<<<<< HEAD

    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() :       ̸      ȭ  ޼   
=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
	
	/**
     * ���� �̸����� Ȯ���ڸ� ������ ���� ���� �̸��� ����
     * 
     * @param fileName ���� �̸�
     * @return ���� ���� �̸�
     */
    public static String subStrName(String fileName) {
    	// FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
<<<<<<< HEAD
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
<<<<<<< HEAD
<<<<<<< HEAD

=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
<<<<<<< HEAD
=======
        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    public static String makeDir() {
<<<<<<< HEAD
<<<<<<< HEAD

    	return "ImgFood\\";
    }
    
    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
       
       boolean hasFile = false;
       
=======
=======
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
    	
<<<<<<< HEAD
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
=======
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
<<<<<<< HEAD
        	
        	log.info("������ �����ϴ�.");
=======
        	log.info("������ �����ϴ�.");
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
        } else {
=======
        	log.info("������ �����ϴ�.");
        } else {
        	log.info("���� ������ �����մϴ�.");
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
<<<<<<< HEAD
=======
        // ������ �����ϴ��� Ȯ���ϰ� ����
>>>>>>> ccdce2e7a9f17201e6bb89e46d18852272cf8bfd
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
