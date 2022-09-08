package com.shaun.sta_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.ResultCode;
import com.shaun.serverbase.exceptionhandler.GuliException;
import com.shaun.sta_service.client.UCenterClient;
import com.shaun.sta_service.entity.StatisticsDaily;
import com.shaun.sta_service.mapper.StatisticsDailyMapper;
import com.shaun.sta_service.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-15
 */
@Service
public class StatisticsDailyServiceImp extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UCenterClient uCenterClient;

    /**
     * 根据日期获取统计数据，向统计表中添加数据
     * @param date 需要统计的日期
     */
    @Override
    // 需要加上事务，如果已经删除掉之前存在的日期了，然后在添加时发生异常，应该回滚数据。
    @Transactional
    public void createStatisticsDataByDate(String date) {
        // 如果表中存在这一日期的数据，删除掉
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", date);
        this.remove(wrapper);

        // 1、通过远程调用获取某一天的注册人数
        int registerNum = uCenterClient.getRegisterNum(date);
        // 2、创建统计对象
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(200, 300));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(300, 400));
        statisticsDaily.setDateCalculated(date);
        // 添加到数据库中
        boolean save = this.save(statisticsDaily);
        if (!save)
            throw new GuliException(ResultCode.ERROR, "向统计表添加数据失败");
    }

    /**
     * 根据前台给定的条件返回统计数据，有两部分数据，一个是日期数组，一个是数量数组
     * @param type 数据类型
     * @param begin 开始日期
     * @param end 结束日期
     * @return 数据封装到Map中
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        // 1、创建条件器，查询出统计数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailyList = this.list(wrapper);

        // 2、因为前端需要的是两个数组，所以需要将数据转换为两个List
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num" :
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num" :
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num" :
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num" :
                    numList.add(statisticsDaily.getCourseNum());
                    break;
            }
        }

        Map<String, Object> map = new HashMap<String, Object>(){
            {
                this.put("dateList", dateList);
                this.put("numList", numList);
            }
        };

        return map;
    }
}
