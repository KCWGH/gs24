package com.gs24.website.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class PreorderVO {
	int preorderId;
	int foodId;
	String memberId;
	int preorderAmount;
	int totalPrice;
	int convenienceId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date pickupDate;

	int isPickUp; // 1 : true | 0 : false
	int isExpiredOrder; // 1 : true | 0 : false
	int appliedGiftCardId;
	int appliedCouponId;
	int writeReview; // 1 : 작성됨 | 0 : 작성 안됨
	
}
