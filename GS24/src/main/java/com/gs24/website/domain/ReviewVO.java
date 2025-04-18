package com.gs24.website.domain;

import java.util.Date;
import java.util.List;

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
	String nickname;
	Date reviewDateCreated;
	
	List<ImgVO> imgList;
}