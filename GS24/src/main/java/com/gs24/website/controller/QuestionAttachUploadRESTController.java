package com.gs24.website.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gs24.website.domain.QuestionAttachDTO;
import com.gs24.website.service.QuestionAttachService;
import com.gs24.website.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value = "/questionAttach")
@Log4j
public class QuestionAttachUploadRESTController {
	
    @Autowired
    private String attachUploadPath;
    
    @Autowired
    private QuestionAttachService questionAttachService;

    // 첨부 파일 생성 
    @PostMapping
    public ResponseEntity<ArrayList<QuestionAttachDTO>> createQuestionAttach(MultipartFile[] files) {
        log.info("createQuestionAttach");

        ArrayList<QuestionAttachDTO> list = new ArrayList<>();

        for (MultipartFile file : files) {
        	
            // UUID 생성
            String chgName = UUID.randomUUID().toString();
            // 파일 저장
            FileUploadUtil.saveFile(attachUploadPath, file, chgName);

            String path = FileUploadUtil.makeDatePath();
            String extension = FileUploadUtil.subStrExtension(file.getOriginalFilename());

            QuestionAttachDTO questionAttachDTO = new QuestionAttachDTO();
            // 파일 경로 설정
            questionAttachDTO.setQuestionAttachPath(path);
            // 파일 실제 이름 설정
            questionAttachDTO.setQuestionAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
            // 파일 변경 이름(UUID) 설정
            questionAttachDTO.setQuestionAttachChgName(chgName);
            // 파일 확장자 설정
            questionAttachDTO.setQuestionAttachExtension(extension);

            list.add(questionAttachDTO);
        }

        return new ResponseEntity<ArrayList<QuestionAttachDTO>>(list, HttpStatus.OK);
    }
    
    // 첨부 파일 다운로드(GET)
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(int questionAttachId) throws IOException {
        log.info("download()");

        // attachId로 상세 정보 조회
        QuestionAttachDTO questionAttachDTO = questionAttachService.getQuestionAttachById(questionAttachId);
        String questionAttachPath = questionAttachDTO.getQuestionAttachPath();
        String questionAttachChgName = questionAttachDTO.getQuestionAttachChgName();
        String questionAttachExtension = questionAttachDTO.getQuestionAttachExtension();
        String questionAttachRealName = questionAttachDTO.getQuestionAttachRealName();
        
        // 서버에 저장된 파일 정보 생성
        String resourcePath = attachUploadPath + File.separator + questionAttachPath + File.separator + questionAttachChgName;
        // 파일 리소스 생성
        Resource resource = new FileSystemResource(resourcePath);
        // 다운로드할 파일 이름을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        String questionAttachName = new String(questionAttachRealName.getBytes("UTF-8"), "ISO-8859-1");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + questionAttachName + "." + questionAttachExtension);

        // 파일을 클라이언트로 전송
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    } // end download()

    // 첨부 파일 삭제
    @PostMapping("/delete")
    public ResponseEntity<Integer> deleteQuestionAttach(String questionAttachPath, String questionAttachChgName) {
        log.info("deleteQuestionAttach()");
        FileUploadUtil.deleteFile(attachUploadPath, questionAttachPath, questionAttachChgName);
        
        return new ResponseEntity<Integer>(1, HttpStatus.OK);
    }
}
