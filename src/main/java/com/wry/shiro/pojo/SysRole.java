package com.wry.shiro.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SysRole {
    private Integer id;

    private String name;

    private List<SysPermission> permissions;
}