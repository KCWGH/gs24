package com.gs24.website.domain;

import java.util.Date;

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
public class ImgReviewVO {
	private int ImgReviewId;
	private int reviewId;
	private String ImgReviewRealName;
	private String ImgReviewChgName;
	private String ImgReviewExtension;
	private Date ImgReviewDateCreated;
	private String ImgReviewPath;
}
