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
public class FavoritesVO {

	int favoritesId;
	String memberId;
	int foodId;
	String foodType;
	String foodName;
}
