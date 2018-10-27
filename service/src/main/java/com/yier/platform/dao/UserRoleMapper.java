package com.yier.platform.dao;

import com.yier.platform.common.model.UserRole;
import com.yier.platform.common.model.UserRolePo;
import com.yier.platform.common.requestParam.UserRoleRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {

    // 根据条件查询用户角色关联列表
    List<UserRolePo> listUserRolePo(UserRoleRequest params);
    // 根据条件查询用户角色关联列表 总数
    int listUserRolePoCount(UserRoleRequest params);

    int deleteUserRoleByUserId(Long userId);

    List<UserRolePo> getUserRoleByUserId(Long userId);




    int deleteByPrimaryKey(UserRoleRequest key);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(UserRoleRequest key);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}