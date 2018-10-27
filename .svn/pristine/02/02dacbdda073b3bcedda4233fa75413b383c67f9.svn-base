package com.yier.platform.dao;

import com.yier.platform.common.model.SystemParameters;
import com.yier.platform.common.requestParam.BaseRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统参数表
 * //封装为 Spring 的数据访问异常类型,Spring 本身提供了一个丰富的并且是与具体的数据访问技术无关的数据访问异常结构，用于封装不同的持久层框架抛出的异常，使得异常独立于底层的框架,
 *             //Spring 会自动创建相应的 BeanDefinition 对象，并注册到 ApplicationContext 中
 * 当一个 Bean 被自动检测到时，会根据那个扫描器的 BeanNameGenerator 策略生成它的 bean 名称。
 */
@Repository
public interface SystemParametersMapper {
    // 根据条件查询基本数据列表
    List<SystemParameters> listSystemParameters(BaseRequest params);
    // 根据条件查询基本数据列表 总数
    int listSystemParametersCount(BaseRequest params);

    String getParameter(@Param("parkey") String parkey);

    int updateSystemParameter(SystemParameters systemParam);

    SystemParameters getSystemParameterByKey(@Param("parkey") String parkey);






    int deleteByPrimaryKey(Long id);

    int insert(SystemParameters record);

    int insertSelective(SystemParameters record);

    SystemParameters selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemParameters record);

    int updateByPrimaryKeyWithBLOBs(SystemParameters record);

    int updateByPrimaryKey(SystemParameters record);
}