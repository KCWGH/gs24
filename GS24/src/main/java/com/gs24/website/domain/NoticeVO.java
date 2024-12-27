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
public class NoticeVO {
	int noticeId; // NOTICE_ID
	String memberId; // MEMBER_ID
	String noticeTitle; // NOTICE_TITLE
	String noticeContent; // NOTICE_CONTENT
	Date noticeDateCreated; // NOTICE_DATE_CREATED
}
