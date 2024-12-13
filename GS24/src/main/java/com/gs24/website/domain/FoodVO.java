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
public class FoodVO {
	private int foodId;
	private String foodType;
	private String foodName;
	private int foodStock;
	private int foodPrice;
	private int foodAvgRating;
	private int foodProtein;
	private int foodFat;
	private int foodCarb;
	private Date registeredDate;
}
