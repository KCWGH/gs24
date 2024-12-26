package com.gs24.website.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Log4j
public class ImgFoodVO {
	private int ImgFoodId;
	private int foodId;
	private String ImgFoodPath;
	private String ImgFoodRealName;
	private String ImgFoodChgName;
	private String ImgFoodExtension;
	private Date ImgFoodDateCreated;
	private MultipartFile file;
	
	public String getPath() {
		return "C:\\Users\\sdedu\\Desktop\\gsproject\\GS24\\src\\main\\webapp" + "\\" + ImgFoodPath;
	}
}
