package com.gs24.website.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVO {
	private int orderId;
    private int foodId;
    private String foodName;
    private String foodType;
    private int orderAmount;
    private Date orderDateCreated;
    private String ownerId;
    private int approvalStatus; /* 0:대기 1:승인 2:거절 */
}
