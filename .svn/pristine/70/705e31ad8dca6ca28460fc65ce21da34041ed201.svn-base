package com.yier.platform.dao;

import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.UserRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    // 根据条件查询列表
    List<User> listUser(UserRequest params);

    // 根据条件查询列表 总数
    int listUserCount(UserRequest params);

    //查询出医生所有手机号列表
    List<UserPo> selectMobilePhoneNumberListForDoctor(UserRequest params);

    //查询出医生所有手机号列表总数
    int selectMobilePhoneNumberListCountForDoctor(UserRequest params);

    //查询出药师所有手机号列表
    List<UserPo> selectMobilePhoneNumberListForPharmacist(UserRequest params);

    //查询出药师所有手机号列表总数
    int selectMobilePhoneNumberListCountForPharmacist(UserRequest params);

    //查询出患者所有手机号列表
    List<UserPo> selectMobilePhoneNumberListForPatient(UserRequest params);

    //查询出患者所有手机号列表总数
    int selectMobilePhoneNumberListCountForPatient(UserRequest params);




    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}