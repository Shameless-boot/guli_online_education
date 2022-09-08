package com.shaun.cms_service.service;

import com.shaun.cms_service.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-01-06
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> queryAllBanner();
}
