package com.gs24.website.util;

import com.gs24.website.domain.MemberVO;
import com.gs24.website.domain.OwnerVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {
	private int pageNum;
	private int pageSize;
	private String type;
	private String keyword;
	private String sortType;
	private String bottomPrice;
	private String topPrice;
	private MemberVO memberVO;
	private OwnerVO ownerVO;

	public Pagination() {
		this.pageNum = 1;
		this.pageSize = 5;
	}

	public String getMemberId() {
		return memberVO.getMemberId();
	}

	public String getOwnerId() {
		return ownerVO.getOwnerId();
	}

	public Pagination(int page, int pageSize) {
		this.pageNum = page;
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (this.pageNum - 1) * this.pageSize + 1;
	}

	public int getEnd() {
		return this.pageNum * this.pageSize;
	}
}
