package com.gs24.website.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gs24.website.domain.PreorderVO;
import com.gs24.website.persistence.ConvenienceFoodMapper;
import com.gs24.website.persistence.PreorderMapper;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class PreordercCheckTask {
	@Autowired
	private ConvenienceFoodMapper convenienceFoodMapper;

	@Autowired
	private PreorderMapper preorderMapper;

	@Scheduled(cron = "30 34 16 * * *")
	public void Task() {
		log.warn("PreorderCheckTask");

		List<PreorderVO> list = preorderMapper.selectOldPreorder();

		if (list.size() == 0) {
			log.warn("task exit");
			return;
		}

		for (PreorderVO preorderVO : list) {
			log.warn("update preorder : " + preorderVO.getPreorderId());
			preorderMapper.updatePreorderByOverPickupDate(preorderVO.getPreorderId());
			convenienceFoodMapper.updateFoodAmountByPreorder(preorderVO.getFoodId(),
					preorderVO.getPreorderAmount() * -1, preorderVO.getConvenienceId());

		}

		log.warn("PreorderCheckTask finish");
	}
}
