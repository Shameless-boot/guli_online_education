package com.shaun.cms_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.cms_service.entity.CrmBanner;
import com.shaun.cms_service.mapper.CrmBannerMapper;
import com.shaun.cms_service.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-01-06
 */
@Service
public class CrmBannerServiceImp extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 根据ID进行降序排序, 返回前两条Banner数据
     * @return banner数据集合
     */
    @Override
    public List<CrmBanner> queryAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // last方法，拼接sql语句
        wrapper.last("limit 2");
        return this.list(wrapper);
    }
}
