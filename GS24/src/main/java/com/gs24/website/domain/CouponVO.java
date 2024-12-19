package com.gs24.website.domain;

import java.sql.Date;

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
	Date couponGrantDate;
	Date couponExpiredDate;
	int discountRate;
	int isValid;
	int isUsed;
}
