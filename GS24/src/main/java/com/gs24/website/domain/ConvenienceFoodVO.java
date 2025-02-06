package com.gs24.website.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ConvenienceFoodVO {
	private int convenienceFoodId;
	private int convenienceId;
	private int foodId;
	private String foodType;
	private String foodName;
	private int foodPrice;
	private int foodProtein;
	private int foodFat;
	private int foodCarb;
	private int foodAmount;
	private int foodAvgRating;
	private int foodReviewCnt;
	private String imgPath;
}
