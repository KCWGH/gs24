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
     * ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ï¿½ï¿½ï¿½ï¿½ È®ï¿½ï¿½ï¿½Ú¸ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
     * 
     * @param fileName ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½
     * @return ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½
     */
    public static String subStrName(String fileName) {
<<<<<<< HEAD
    	// FilenameUtils.normalize() : ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ ï¿½ï¿½ï¿½ï¿½È­ ï¿½Þ¼ï¿½ï¿½ï¿½
=======
    	// FilenameUtils.normalize() : ÆÄÀÏ ÀÌ¸§ Á¤±ÔÈ­ ¸Þ¼­µå
=======
   
   /**
     *       Ì¸      È®   Ú¸                    Ì¸        
     * 
     * @param fileName       Ì¸ 
     * @return            Ì¸ 
     */
    public static String subStrName(String fileName) {
       // FilenameUtils.normalize() :       Ì¸      È­  Þ¼   
>>>>>>> Stashed changes
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
<<<<<<< HEAD
     * ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ï¿½ï¿½ï¿½ï¿½ È®ï¿½ï¿½ï¿½Ú¸ï¿½ ï¿½ï¿½ï¿½ï¿½
=======
<<<<<<< Updated upstream
     * ÆÄÀÏ ÀÌ¸§¿¡¼­ È®ÀåÀÚ¸¦ ÃßÃâ
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
     * 
     * @param fileName ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½
     * @return È®ï¿½ï¿½ï¿½ï¿½
     */
    public static String subStrExtension(String fileName) {
        // ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ '.'ï¿½ï¿½ ï¿½Îµï¿½ï¿½ï¿½ï¿½ï¿½ Ã£ï¿½ï¿½ï¿½Ï´ï¿½.
        int dotIndex = fileName.lastIndexOf('.');

<<<<<<< HEAD
        // '.' ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ú¿ï¿½ï¿½ï¿½ È®ï¿½ï¿½ï¿½Ú·ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Õ´Ï´ï¿½.
=======
        // '.' ÀÌÈÄÀÇ ¹®ÀÚ¿­À» È®ÀåÀÚ·Î ÃßÃâÇÕ´Ï´Ù.
=======
     *       Ì¸      È®   Ú¸      
     * 
     * @param fileName       Ì¸ 
     * @return È®    
     */
    public static String subStrExtension(String fileName) {
        //       Ì¸             '.'    Îµ      Ã£   Ï´ .
        int dotIndex = fileName.lastIndexOf('.');

        // '.'           Ú¿    È®   Ú·       Õ´Ï´ .
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
     * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
     * 
     * @param uploadPath ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Îµï¿½ ï¿½ï¿½ï¿½
     * @param file ï¿½ï¿½ï¿½Îµï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
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
     * @param uploadPath         Îµ     
     * @param file    Îµ       
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
        	log.info("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ï´ï¿½.");
=======
<<<<<<< Updated upstream
        	log.info("ÆÄÀÏÀÌ ¾ø½À´Ï´Ù.");
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
        } else {
        	log.info("ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Õ´Ï´ï¿½.");
        	hasFile = true;
=======
           log.info("            Ï´ .");
        } else {
           log.info("                 Õ´Ï´ .");
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
     * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
=======
<<<<<<< Updated upstream
     * ÆÄÀÏÀ» »èÁ¦
>>>>>>> c366c08dc1ff87280f5da0a1dbabf6a230862cb9
     * 
     * @param uploadPath ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Îµï¿½ ï¿½ï¿½ï¿½
     * @param path ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Â¥ ï¿½ï¿½ï¿½
     * @param chgName ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ì¸ï¿½
     */
    public static void deleteFile(String uploadPath,String chgName) {
        // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ã¼ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        // ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ã¼ ï¿½ï¿½ï¿½ï¿½
        File file = new File(fullPath);
        
<<<<<<< HEAD
        // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ï´ï¿½ï¿½ï¿½ È®ï¿½ï¿½ï¿½Ï°ï¿½ ï¿½ï¿½ï¿½ï¿½
=======
        // ÆÄÀÏÀÌ Á¸ÀçÇÏ´ÂÁö È®ÀÎÇÏ°í »èÁ¦
=======
     *            
     * 
     * @param uploadPath         Îµ     
     * @param path                Â¥    
     * @param chgName             Ì¸ 
     */
    public static void deleteFile(String uploadPath,String chgName) {
        //                 Ã¼         
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        //        Ã¼     
        File file = new File(fullPath);
        
        //             Ï´    È®   Ï°      
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
