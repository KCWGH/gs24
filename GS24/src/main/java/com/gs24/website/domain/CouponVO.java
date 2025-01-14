package com.gs24.website.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
public class CouponVO {

	int couponId;
	String foodType;
	String couponName;
	int couponAmount;
	char discountType;
	int discountValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	Date couponExpiredDate;
	int isDuplicateAllowed;
	int isAvailable;
}
