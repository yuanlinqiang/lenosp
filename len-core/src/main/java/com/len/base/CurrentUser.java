package com.len.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrentUser implements Serializable {
    private String id;

    private String username;

    private String password;

    private Integer age;

    private String email;

    private String photo;

    private String realName;

    private String createBy;

    private String updateBy;

    private Date createDate;

    private Date updateDate;

    private Byte delFlag;

   private List<CurrentMenu> currentMenuList;
   private List<CurrentRole> currentRoleList;
    private static final long serialVersionUID = 1L;

    public CurrentUser(String id, String username, Integer age, String email, String photo,
        String realName) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.photo = photo;
        this.realName = realName;
    }

    public CurrentUser() {
    }
}