package com.scrh.shirodemo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String perms;
}
