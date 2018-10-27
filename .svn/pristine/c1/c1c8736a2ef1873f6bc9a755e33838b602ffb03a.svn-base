package com.yier.platform.dao;

import com.yier.platform.common.model.Role;
import com.yier.platform.common.requestParam.RoleRequest;

import java.util.List;

public interface RoleMapper {


    List<Role> selectRoles(RoleRequest params);

    int selectRolesCount(RoleRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}