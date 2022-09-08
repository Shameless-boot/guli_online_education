package com.shaun.sta_service.service;

import com.shaun.sta_service.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-15
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    // 根据日期获取统计数据，向统计表中添加数据
    void createStatisticsDataByDate(String date);

    // 根据前台给定的条件返回统计数据，有两部分数据，一个是日期数组，一个是数量数组
    Map<String, Object> getShowData(String type, String begin, String end);
}
