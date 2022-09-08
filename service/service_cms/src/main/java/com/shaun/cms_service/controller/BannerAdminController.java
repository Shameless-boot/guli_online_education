package com.shaun.cms_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.cms_service.entity.CrmBanner;
import com.shaun.cms_service.service.CrmBannerService;
import com.shaun.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目后台Banner控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-01-06
 */
@RestController
@RequestMapping("/cms_service/admin/banner")
@Api(description = "项目后台Banner接口")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/{id}")
    @ApiOperation("根据banner的ID获取Banner信息")
    public Result getBannerById(@PathVariable("id") @ApiParam("BannerID") String id) {
        CrmBanner banner = bannerService.getById(id);
        return banner != null ? Result.Ok().data("item", banner): Result.Error();
    }

    @GetMapping("/page/{currentPage}/{size}")
    @ApiOperation("接受前台发送过来的当前页和每页显示数量返回分页数据")
    public Result getBannerPage(@PathVariable("currentPage") @ApiParam("当前页") long currentPage,
                                @PathVariable("size") @ApiParam("每页显示数量") long size) {
        Page<CrmBanner> page = new Page<>(currentPage, size);
        this.bannerService.page(page);
        System.out.println(page);
        return Result.Ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据banner的ID删除Banner信息")
    public Result deleteBannerById(@PathVariable("id") @ApiParam("BannerID") String id) {
        boolean removeFlag = bannerService.removeById(id);
        return removeFlag ? Result.Ok() : Result.Error();
    }

    @PostMapping("")
    @ApiOperation("添加Banner信息")
    public Result insertBanner(@RequestBody @ApiParam("Banner对象") CrmBanner banner) {
        boolean saveFlag = this.bannerService.save(banner);
        return saveFlag ? Result.Ok() : Result.Error();
    }

    @PutMapping("")
    @ApiOperation("修改Banner信息")
    public Result updateBanner(@RequestBody @ApiParam("Banner对象") CrmBanner banner) {
        boolean updateFlag = this.bannerService.updateById(banner);
        return updateFlag ? Result.Ok() : Result.Error();
    }
}

