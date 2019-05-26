package com.wry.shiro.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {
    private Integer id;

    private String username;

    private String password;

    private List<SysRole> roles;

    private List<SysPermission> permissions;
}