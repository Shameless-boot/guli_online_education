package com.shaun.sta_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.sta_service.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/sta_service/statisticsDaily")
@Api(description = "统计数据控制层")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/createStatisticsDataByDate/{date}")
    @ApiOperation("根据给定日期添加统计数据到数据库中")
    public Result createStatisticsDataByDate(@PathVariable("date") @ApiParam("日期") String date) {
        this.statisticsDailyService.createStatisticsDataByDate(date);
        return Result.Ok();
    }

    @GetMapping("/showData/{type}/{begin}/{end}")
    @ApiOperation("根据前台给定的条件返回统计数据，有两部分数据，一个是日期数组，一个是数量数组")
    public Result showData(@PathVariable("type") @ApiParam("类型") String type,
                           @PathVariable("begin") @ApiParam("开始日期") String begin,
                           @PathVariable("end") @ApiParam("结束日期") String end) {
        Map<String, Object> map = this.statisticsDailyService.getShowData(type, begin, end);
        return Result.Ok().data(map);
    }
}

