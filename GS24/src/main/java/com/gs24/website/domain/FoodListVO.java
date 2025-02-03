package com.gs24.website.domain;

import java.util.List;

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
public class FoodListVO {
	private int foodId;
	private String foodType;
	private String foodName;
	private int foodPrice;
	private int foodProtein;
	private int foodFat;
	private int foodCarb;
	private int foodStock;
	private int isSelling;
	
	private List<ImgVO> imgList;
}
