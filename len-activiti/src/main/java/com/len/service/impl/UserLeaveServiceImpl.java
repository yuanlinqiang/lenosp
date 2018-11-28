package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.UserLeave;
import com.len.mapper.UserLeaveMapper;
import com.len.service.UserLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2018/1/21.
 * @email 154040976@qq.com
 */
@Service
public class UserLeaveServiceImpl extends BaseServiceImpl<UserLeave,String> implements
    UserLeaveService {

  @Autowired
  UserLeaveMapper userLeaveMapper;

  @Override
  public BaseMapper<UserLeave,String> getMappser() {
    return userLeaveMapper;
  }
}
