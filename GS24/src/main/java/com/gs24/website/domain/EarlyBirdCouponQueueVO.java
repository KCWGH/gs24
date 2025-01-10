package com.gs24.website.domain;

import java.util.Date;

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
public class EarlyBirdCouponQueueVO {

	int queueId;
	int couponId;
	String memberId;
	Date queueDateCreated;
}
