package com.gs24.website.domain;

import java.util.Date;

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
	Date pickupDate;
	int isPickUp; 	// 1 : true | 0 : false
	int isExpiredOrder;	// 1 : true | 0 : false
}
