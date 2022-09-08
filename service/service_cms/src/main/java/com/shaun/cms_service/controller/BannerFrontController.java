package com.shaun.cms_service.controller;


import com.shaun.cms_service.entity.CrmBanner;
import com.shaun.cms_service.service.CrmBannerService;
import com.shaun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 项目前台Banner控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/cms_service/front/banner")
@Api(description = "项目前台Banner接口")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    @Cacheable(key = "'selectBannerList'", value = "banner")
    @ApiOperation("获取前两条Banner信息")
    public Result getAllBanner() {
        List<CrmBanner> bannerList = bannerService.queryAllBanner();
        return Result.Ok().data("items", bannerList);
    }
}

