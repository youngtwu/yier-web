package com.yier.platform.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.UserRole;
import com.yier.platform.common.model.UserRolePo;
import com.yier.platform.common.requestParam.UserRoleRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.dao.UserRoleMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@ApiModel(value = "用户角色 service")
@Service
public class UserRoleService {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);
    @Autowired
    private UserRoleMapper daoUserRoleMapper;
    @Autowired
    private RedisService redisService;

/*    public <T extends BaseJsonObject> List<T> getListAllT(String constantAllKey) {
        List<T> listAllUserRolePo =  redisService.getJsonObjectByKey(constantAllKey, new TypeReference<List<T>>() {
        });
        if(listAllUserRolePo==null){
            switch (constantAllKey){
                case ConstantAll.REDIS_ALL_USER_ROLE_PO:
                    UserRoleRequest params = new UserRoleRequest();
                    params.setStatus("0");
                    listAllUserRolePo = this.listUserRolePo(params);
                break;
            }
//            redisService.setJsonObjectBy(key,listAllUserRolePo,12L,TimeUnit.HOURS);
            redisService.setJsonObjectBy(ConstantAll.REDIS_ALL_USER_ROLE_PO,listAllUserRolePo,30L,TimeUnit.SECONDS);
            logger.info("目前DB调用查询情况是：key-{} value：{}",constantAllKey,JsonUtils.toJsonString(listAllUserRolePo));
        }
        return listAllUserRolePo;
    }*/

    @ApiOperation(value = "获取DB中所有的用户角色关联列表")
    public List<UserRolePo> getListAllUserRolePo() {
        //updateListAllUserRolePo();
        List<UserRolePo> listAllUserRolePo = redisService.getJsonObjectByKey(ConstantAll.REDIS_ALL_USER_ROLE_PO, new TypeReference<List<UserRolePo>>() {
        });
        if (listAllUserRolePo == null || listAllUserRolePo.size()==0) {
            UserRoleRequest params = new UserRoleRequest();
            params.setStatus("0");
            listAllUserRolePo = this.listUserRolePo(params);
            redisService.setJsonObjectBy(ConstantAll.REDIS_ALL_USER_ROLE_PO, listAllUserRolePo, 12L, TimeUnit.HOURS);
//            redisService.setJsonObjectBy(ConstantAll.REDIS_ALL_USER_ROLE_PO,listAllUserRolePo,30L,TimeUnit.SECONDS);
            logger.info("目前DB调用查询情况是：key-{} value：{}", ConstantAll.REDIS_ALL_USER_ROLE_PO, JsonUtils.toJsonString(listAllUserRolePo));
        }
        return listAllUserRolePo;
    }

    @ApiOperation(value = "更新数据库中DB中所有的用户角色关联列表")
    public void updateListAllUserRolePo() {
        this.redisService.deleteRedisByKey(ConstantAll.REDIS_ALL_USER_ROLE_PO);
    }


    @ApiOperation(value = "根据条件查询用户角色关联列表")
    public List<UserRolePo> listUserRolePo(UserRoleRequest params) {
        return this.daoUserRoleMapper.listUserRolePo(params);
    }

    @ApiOperation(value = "根据条件查询用户角色关联列表 总数")
    public int listUserRolePoCount(UserRoleRequest params) {
        return this.daoUserRoleMapper.listUserRolePoCount(params);
    }

    @ApiOperation(value = "根据用户id分配用户角色")
    @Transactional
    public List<UserRole> assignmentRoleByUserId(List<UserRole> userRoles) {

        if (userRoles != null && userRoles.size() > 0) {
            //先删除在增加
            for (UserRole userRole : userRoles) {
                this.daoUserRoleMapper.deleteUserRoleByUserId(userRole.getUserId());
            }

            for (UserRole userRole : userRoles) {
                userRole.setRoleId(userRole.getRoleId());
                userRole.setUserId(userRole.getUserId());
                userRole.setRemarks("根据用户id分配用户角色");
                this.daoUserRoleMapper.insertSelective(userRole);
            }
        }
        return userRoles;
    }

    @ApiOperation(value = "根据用户id查询用户角色")
    public List<UserRolePo> getUserRoleByUserId(Long userId) {
        return this.daoUserRoleMapper.getUserRoleByUserId(userId);
    }
}
