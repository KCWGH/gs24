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
	String couponName;
	String memberId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	Date couponGrantDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	Date couponExpiredDate;
	int discountRate;
	int isValid;
	int isUsed;
	private String couponImageUrl;

}
