package com.gs24.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
public class uploadImgFoodUtil {

    /**
     * 파일 이름에서 확장자를 제외한 실제 파일 이름만 추출
     * 
     * @param fileName 파일 이름
     * @return 실제 파일 이름
     */
    public static String subStrName(String fileName) {
        // FilenameUtils.normalize() : 파일 경로를 정리하여 안전한 경로로 만듦
        String normalizeName = FilenameUtils.normalize(fileName);
        int dotIndex = normalizeName.lastIndexOf('.');

        String realName = normalizeName.substring(0, dotIndex);
        return realName;
    }
    
    /**
     * 파일 이름에서 확장자만 추출
     * 
     * @param fileName 파일 이름
     * @return 확장자
     */
    public static String subStrExtension(String fileName) {
        // 파일 이름에서 마지막 '.'의 인덱스를 찾고, 그 이후의 문자열을 확장자로 추출
        int dotIndex = fileName.lastIndexOf('.');

        String extension = fileName.substring(dotIndex + 1);
        return extension;
    }
    
    public static String makeDir() {
        return "ImgFood\\";
    }
    
    /**
     * 파일을 저장하는 메서드
     * 
     * @param uploadPath 업로드할 경로
     * @param file 업로드할 파일
     * @param chgName 변경된 파일 이름
     */
    public static boolean saveFile(String uploadPath, MultipartFile file, String chgName) {
        boolean hasFile = false;
        
        // 업로드 경로의 실제 디렉토리 생성
        File realUploadPath = new File(uploadPath, makeDir());
        if (!realUploadPath.exists()) {
            realUploadPath.mkdirs();
            log.info(realUploadPath.getPath() + " successfully created.");
        } else {
            log.info(realUploadPath.getPath() + " already exists.");
        }
        
        // 저장할 파일 경로 설정
        File saveFile = new File(realUploadPath, chgName);
        
        // 파일이 이미 존재하는지 체크
        if (!saveFile.exists()) {
            log.info("파일을 업로드합니다.");
        } else {
            log.info("파일이 이미 존재합니다.");
            hasFile = true;
        }
        
        // 파일을 실제로 업로드
        try {
            file.transferTo(saveFile);
            log.info("file upload success");
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        
        return hasFile;
    }
    
    /**
     * 파일을 삭제하는 메서드
     * 
     * @param uploadPath 업로드 경로
     * @param chgName 변경된 파일 이름
     */
    public static void deleteFile(String uploadPath, String chgName) {
        // 삭제할 파일 경로 생성
        String fullPath = uploadPath + File.separator + makeDir() + chgName;
        
        // 파일 객체 생성
        File file = new File(fullPath);
        
        // 파일 존재 여부 체크 후 삭제
        if (file.exists()) {
            if (file.delete()) {
                log.info(fullPath + " file delete success.");
            } else {
                log.info(fullPath + " file delete failed.");
            }
        } else {
            log.info(fullPath + " file not found.");
        }
    }
}
