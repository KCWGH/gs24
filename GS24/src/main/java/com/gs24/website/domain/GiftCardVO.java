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
public class GiftCardVO {
	int giftCardId;
	String giftCardName;
	String memberId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	Date giftCardGrantDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	Date giftCardExpiredDate;
	int discountValue;
	String foodType;
	int isUsed;

}
