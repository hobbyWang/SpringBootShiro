package com.wry.shiro.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wry.shiro.pojo.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> findByAll(SysRole sysRole);


}