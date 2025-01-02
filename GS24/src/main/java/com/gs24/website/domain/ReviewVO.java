package com.gs24.website.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReviewVO {
	int reviewId;
	String memberId;
	String reviewTitle;
	String reviewContent;
	int foodId;
	int reviewRating;
	Date reviewDateCreated;
	
	String reviewImgPath;
}