package com.yier.platform.service;

import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.UserAddress;
import com.yier.platform.common.requestParam.UserAddressRequest;
import com.yier.platform.common.util.OrderIdUtils;
import com.yier.platform.dao.UserAddressMapper;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@ApiModel(value = "用户角色 service")
@Service
public class UserAddressService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);
    @Autowired
    private UserAddressMapper daoUserAddressMapper;


    @Transactional
    public UserAddress editUserAddress(UserAddress record){
        Assert.isTrue(record.getId()!=null,"请传递有效的ID!");

        if(StringUtils.isNotBlank(record.getProvinceName())){
            StringBuilder sb = new StringBuilder();
            sb.append(record.getProvinceName()).append(record.getCityName()).append(record.getAreaName()).append(record.getAddress());
            record.setAddressJoin(sb.toString());
        }
        UserAddressRequest params = new UserAddressRequest();
        params.setTypeId(record.getTypeId());
        params.setUserTableId(record.getUserTableId());
        params.setStatus(ConstantAll.STATUS_0);
        List<UserAddress> list = this.listUserAddress(params);
        if(list.size()>1 && record.getDefaultValue()!=null){
            if(list.stream().filter(t->t.getId().longValue()==record.getId().longValue() && !StringUtils.equalsIgnoreCase(t.getDefaultValue(),record.getDefaultValue()) ).count()>0){
                if(StringUtils.equalsIgnoreCase(record.getDefaultValue(),ConstantAll.STATUS_OPEN)){
                    list.forEach(t->{
                        if(StringUtils.equalsIgnoreCase(t.getDefaultValue(),ConstantAll.STATUS_OPEN)){
                            t.setDefaultValue(ConstantAll.STATUS_CLOSE);
                            this.updateByPrimaryKeySelective(t);
                        }
                    });
                }
            }
        }
        daoUserAddressMapper.updateByPrimaryKeySelective(record);
        return record;
    }
    @Transactional
    public UserAddress addNewUserAddress(UserAddress record){
        record.setAddressCodeNo(OrderIdUtils.genOrderIdWithUUID("A"));
        if(StringUtils.isNotBlank(record.getProvinceName())){
            StringBuilder sb = new StringBuilder();
            sb.append(record.getProvinceName()).append(record.getCityName()).append(record.getAreaName()).append(record.getAddress());
            record.setAddressJoin(sb.toString());
        }
        if(StringUtils.equalsIgnoreCase(record.getDefaultValue(),ConstantAll.STATUS_OPEN)){
            UserAddressRequest params = new UserAddressRequest();
            params.setTypeId(record.getTypeId());
            params.setUserTableId(record.getUserTableId());
            params.setStatus(ConstantAll.STATUS_0);
            List<UserAddress> list = this.listUserAddress(params);
            list.forEach(t->{
                if(StringUtils.equalsIgnoreCase(t.getDefaultValue(),ConstantAll.STATUS_OPEN)){
                    t.setDefaultValue(ConstantAll.STATUS_CLOSE);
                    this.updateByPrimaryKeySelective(t);
                }
            });
        }
        daoUserAddressMapper.insertSelective(record);
        return record;
    }
    // 根据条件对象params查询列表中第一个没有的话,默认整个是New类
    public UserAddress getFirstUserAddress(UserAddressRequest params){
        List<UserAddress> userAddressList = daoUserAddressMapper.listUserAddress(params);
        return userAddressList.stream().findFirst().orElse(null);
    }
    // 根据条件对象params查询列表
    public List<UserAddress> listUserAddress(UserAddressRequest params){
        return daoUserAddressMapper.listUserAddress(params);
    }
    // 根据条件查询列表 总数
    public int listUserAddressCount(UserAddressRequest params){
        return daoUserAddressMapper.listUserAddressCount(params);
    }

    public int deleteByPrimaryKey(Long id){
        return daoUserAddressMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(UserAddress record){
        return daoUserAddressMapper.insertSelective(record);
    }

    public UserAddress selectByPrimaryKey(Long id){
        return daoUserAddressMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(UserAddress record){
        return daoUserAddressMapper.updateByPrimaryKeySelective(record);
    }

}
