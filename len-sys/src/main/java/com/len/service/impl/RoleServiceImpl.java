package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRole;
import com.len.mapper.SysRoleMapper;
import com.len.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2017/12/19.
 * @email 154040976@qq.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole,String> implements RoleService {

  @Autowired
  private SysRoleMapper roleMapper;

  @Override
  public BaseMapper<SysRole, String> getMappser() {
    return roleMapper;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return roleMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysRole record) {
    record=super.addValue(record,true);
    return roleMapper.insert(record);
  }

 /* @Override
  public int insertSelective(SysRole record) {
    return roleMapper.insertSelective(record);
  }*/

  @Override
  public SysRole selectByPrimaryKey(String id) {
    return roleMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysRole record) {
    return roleMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysRole record) {
    return roleMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysRole> selectListByPage(SysRole sysRole) {
    return roleMapper.selectListByPage(sysRole);
  }
}
