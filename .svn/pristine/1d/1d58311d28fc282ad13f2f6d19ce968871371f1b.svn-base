package com.yier.platform.dao;

import com.yier.platform.common.model.UserAddress;
import com.yier.platform.common.requestParam.UserAddressRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressMapper {
    // 根据条件对象params查询列表
    List<UserAddress> listUserAddress(UserAddressRequest params);
    // 根据条件查询列表 总数
    int listUserAddressCount(UserAddressRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
}