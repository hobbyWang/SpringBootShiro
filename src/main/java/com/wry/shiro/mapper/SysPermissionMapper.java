package com.wry.shiro.mapper;
import org.apache.ibatis.annotations.Param;

import com.wry.shiro.pojo.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findByAll(SysPermission sysPermission);


}