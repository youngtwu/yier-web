package com.yier.platform.dao;

import com.yier.platform.common.model.UserWalletSerial;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletSerialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserWalletSerial record);

    int insertSelective(UserWalletSerial record);

    UserWalletSerial selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserWalletSerial record);

    int updateByPrimaryKey(UserWalletSerial record);
}