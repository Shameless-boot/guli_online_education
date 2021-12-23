package com.shaun.edu_service.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author Shaun
 * @Date 2021/12/22 17:19
 * @Description: 自动填充类
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入数据时自动填充添加时间和修改时间字段
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, new Date());
        this.strictInsertFill(metaObject, "gmtModified", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModified", Date.class, new Date());
    }
}
