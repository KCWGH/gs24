package com.gs24.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gs24.website.persistence.MembershipMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MembershipServiceImple implements MembershipService {

	@Autowired
	private MembershipMapper membershipMapper;

	@Override
	@Transactional(value = "transactionManager")
	@Scheduled(cron = "0 0 9 1 * *") // 매월 1일 오전 9시
	public void membership() {
		membershipMapper.membershipEvaluation();
		membershipMapper.membershipPromotion();
		membershipMapper.initializeSpentAmount();
	}
}
