package com.gs24.website.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderHistoryVO {
	private int orderId;
    private int foodId;
    private String foodType;
	private String foodName;
    private int orderAmount;
    private Date orderDateCreated;
    private String ownerId;
}
