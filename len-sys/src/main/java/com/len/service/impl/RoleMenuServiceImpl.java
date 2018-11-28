package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRoleMenu;
import com.len.mapper.SysRoleMenuMapper;
import com.len.service.RoleMenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2017/12/28.
 * @email 154040976@qq.com
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu,String> implements
    RoleMenuService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Override
    public BaseMapper<SysRoleMenu, String> getMappser() {
        return roleMenuMapper;
    }

    @Override
    public List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.selectByCondition(sysRoleMenu);
    }

    @Override
    public int selectCountByCondition(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.selectCountByCondition(sysRoleMenu);
    }

    @Override
    public int deleteByPrimaryKey(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.deleteByPrimaryKey(sysRoleMenu);
    }
}
