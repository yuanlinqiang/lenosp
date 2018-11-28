package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu,String> {

    /**获取元节点*/
    List<SysMenu> getMenuNotSuper();

    /**
     * 获取子节点
     * @return
     */
    List<SysMenu> getMenuChildren(String id);

    List<SysMenu> getMenuChildrenAll(String id);

    /**
     * 根据用户获取所有菜单
     * @param id
     * @return
     */
    List<SysMenu> getUserMenu(@Param("id") String id);
}