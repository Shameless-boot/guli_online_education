package com.shaun.sta_service.schedule;

import com.shaun.sta_service.service.StatisticsDailyService;
import com.shaun.sta_service.utls.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author Shaun
 * @Date 2022/2/15 1:15
 * @Description:
 */

@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /*@Scheduled(cron = "0/5 * * * * ? ")
    public void task01() {
        System.out.println("task");
    }*/

    @Scheduled(cron = "0 0 1 * * ?")
    public void task02() {
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        this.statisticsDailyService.createStatisticsDataByDate(date);
    }
}
