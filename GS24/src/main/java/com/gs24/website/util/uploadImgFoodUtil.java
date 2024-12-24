package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {
	
	/**
     * ���� �̸����� Ȯ���ڸ� ������ ���� ���� �̸��� ����
     * 
     * @param fileName ���� �̸�
     * @return ���� ���� �̸�
     */
    public static String subStrName(String fileName) {
    	// FilenameUtils.normalize() : ���� �̸� ����ȭ �޼���
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
     * ���� �̸����� Ȯ���ڸ� ����
     * 
     * @param fileName ���� �̸�
     * @return Ȯ����
     */
    public static String subStrExtension(String fileName) {
        // ���� �̸����� ������ '.'�� �ε����� ã���ϴ�.
        int dotIndex = fileName.lastIndexOf('.');

        // '.' ������ ���ڿ��� Ȯ���ڷ� �����մϴ�.
        String extension = fileName.substring(dotIndex + 1);

        return extension;
    }
    
    public static String makeDir() {
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
    	
        File realUploadPath = new File(uploadPath,makeDir());
        if (!realUploadPath.exists()) {
            realUploadPath.mkdirs();
            log.info(realUploadPath.getPath() + " successfully created.");
        } else {
            log.info(realUploadPath.getPath() + " already exists.");
        }
        
        File saveFile = new File(realUploadPath, chgName);
        if(!saveFile.exists()) {
        	log.info("������ �����ϴ�.");
        } else {
        	log.info("���� ������ �����մϴ�.");
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
    
    /**
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
