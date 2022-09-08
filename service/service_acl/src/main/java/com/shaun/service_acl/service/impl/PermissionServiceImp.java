package com.shaun.service_acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.service_acl.entity.Permission;
import com.shaun.service_acl.entity.RolePermission;
import com.shaun.service_acl.entity.User;
import com.shaun.service_acl.helper.MemuHelper;
import com.shaun.service_acl.helper.PermissionHelper;
import com.shaun.service_acl.mapper.PermissionMapper;
import com.shaun.service_acl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.service_acl.service.RolePermissionService;
import com.shaun.service_acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-19
 */
@Service
public class PermissionServiceImp extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;
    /**
     * 返回所有菜单列表，按照树形结构返回
     * @return 所有菜单列表
     */
    @Override
    public List<Permission> queryAllMenu() {
        // 1、查找数据库中所有菜单
        List<Permission> permissionList = this.list();

        List<Permission> resultList = new ArrayList<>();

        // 2、遍历整个菜单列表，找到所有一级列表，并将其所有下级列表封装，然后存储在结果集合中
        for (Permission permission : permissionList) {
            if (permission.getPid().equals("0")) {
                // 2.1、将一级列表的level置为1
                permission.setLevel(1);
                // 2.2、找到一级列表的所有下级列表
                resultList.add(findChildrenById(permission, permissionList));
            }
        }

        return resultList;
    }

    /**
     * 递归查找根列表的所有子列表，最后将根列表返回
     * @param root 根列表
     * @param permissionList 所有菜单列表
     * @return 根列表，方法结束之后，根列表的 children 集合被找到了
     */
    private Permission findChildrenById(Permission root, List<Permission> permissionList) {
        // 1、遍历整个菜单列表，查找根列表的所有下级列表
        for (Permission permission : permissionList) {
            if (permission.getPid().equals(root.getId())) {
                // 2、设置下级列表的level为上级列表 + 1
                permission.setLevel(root.getLevel() + 1);
                if (root.getChildren() == null)
                    root.setChildren(new ArrayList<>());
                // 3、递归查找该下级列表的所有列表，最后将其添加给上级列表
                root.getChildren().add(findChildrenById(permission, permissionList));
            }
        }
        return root;
    }

    @Override
    @Transactional
    public void removePermissionById(String id) {
        // 1、创建存储批量删除用的id列表
        List<String> idList = new ArrayList<>();
        idList.add(id);

        // 2、查询所有id列表的下级列表id，将其封装在idList中
        findChildrenId(id, idList);

        System.out.println(idList);

        // 3、执行批量删除
        this.removeByIds(idList);
    }

    private void findChildrenId(String id, List<String> idList) {
        // 1、查询所有 pid = id 的下级列表，只需要返回id列即可
        List<Permission> children = this.list(new QueryWrapper<Permission>().eq("pid", id).select("id"));

        // 2、将所有下级列表的id添加到idList中，然后继续递归查询更下一层的下级列表
        children.forEach(child -> {
            idList.add(child.getId());
            // 3、递归查询更下一级列表的id
            findChildrenId(child.getId(), idList);
        });
    }

    /**
     * 给角色授予权限
     * @param roleId 角色ID
     * @param permissionIds 菜单权限ID数组
     */
    @Override
    public void assignPermissionsToRole(String roleId, String[] permissionIds) {
        // 1、创建集合用于批量添加
        List<RolePermission> rolePermissionList = new ArrayList<>();

        // 2、遍历菜单权限ID数组，创建RolePermission对象，添加到集合中
        Arrays.stream(permissionIds).forEach(item -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(item);

            rolePermissionList.add(rolePermission);
        });

        // 3、批量添加
        this.rolePermissionService.saveBatch(rolePermissionList);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }


    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = build(allPermissionList);
        return permissionList;
    }

    private List<Permission> build(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildrenById(treeNode,treeNodes));
            }
        }
        return trees;
    }
}
