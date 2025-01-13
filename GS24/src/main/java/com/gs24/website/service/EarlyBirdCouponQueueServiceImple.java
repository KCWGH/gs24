package com.gs24.website.service;

import java.sql.Connection;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gs24.website.domain.EarlyBirdCouponQueueVO;
import com.gs24.website.persistence.EarlyBirdCouponQueueMapper;

@Service
public class EarlyBirdCouponQueueServiceImple implements EarlyBirdCouponQueueService {

    @Autowired
    private EarlyBirdCouponQueueMapper earlyBirdCouponQueueMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int queueOne(EarlyBirdCouponQueueVO earlyBirdCouponQueueVO) {
        return earlyBirdCouponQueueMapper.insertQueue(earlyBirdCouponQueueVO);
    }

    @Override
    public void setAmount(int earlyBirdCouponId, int earlyBirdCouponAmount) {
        String sql = "CREATE OR REPLACE TRIGGER QUEUE_LIMIT " +
                     "BEFORE INSERT ON EARLY_BIRD_COUPON_QUEUE " +
                     "FOR EACH ROW " +
                     "DECLARE " +
                     "ROW_COUNT INT; " +
                     "BEGIN " +
                     "SELECT COUNT(*) INTO ROW_COUNT FROM EARLY_BIRD_COUPON_QUEUE " +
                     "WHERE COUPON_ID = " + earlyBirdCouponId + "; " +
                     "IF ROW_COUNT >= " + earlyBirdCouponAmount + " THEN " +
                     "RAISE_APPLICATION_ERROR(-20001, '제한된 행 개수에 도달했습니다.'); " +
                     "END IF; " +
                     "END;";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("트리거 생성에 실패했습니다.", e);
        }
    }

}
