package com.wry.shiro.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.wry.shiro.pojo.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByUsername(@Param("username")String username);


}