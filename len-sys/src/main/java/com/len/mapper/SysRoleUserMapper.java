package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysRoleUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleUserMapper extends BaseMapper<SysRoleUser,String> {

    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    int selectCountByCondition(SysRoleUser sysRoleUser);
}