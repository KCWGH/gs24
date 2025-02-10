package com.gs24.website.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ReviewRatingVO {
	private int avgRating;
	private int reviewCnt;
}
