package com.shaun.service_acl.service;

import com.alibaba.fastjson.JSONObject;
import com.shaun.service_acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-19
 */
public interface PermissionService extends IService<Permission> {

    // 获取所有菜单数据，按树形结构返回
    List<Permission> queryAllMenu();

    // 根据id删除相关的所有菜单
    void removePermissionById(String id);

    // 给角色授予权限
    void assignPermissionsToRole(String roleId, String[] permissionIds);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String userId);

    List<Permission> selectAllMenu(String roleId);
}
