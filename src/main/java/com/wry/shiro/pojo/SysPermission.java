package com.wry.shiro.pojo;

import lombok.Data;

@Data
public class SysPermission {
    private Integer id;

    private String name;

    private String descritpion;

    private String url;

    private Integer pid;

    private String method;
}