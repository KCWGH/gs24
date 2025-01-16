package com.gs24.website.domain;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImgVO {
	private int ImgId;
	private int foreignId;
	private String ImgRealName;
	private String ImgChgName;
	private String ImgExtension;
	private Date ImgDateCreated;
	private String ImgPath;
	
	private String type;
}
