package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysJob;
import com.len.mapper.SysJobMapper;
import com.len.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuxiaomeng
 * @date 2018/1/6.
 * @email 154040976@qq.com
 */
@Service
public class JobServiceImpl  extends BaseServiceImpl<SysJob,String> implements JobService {

  @Autowired
  SysJobMapper jobMapper;
  @Override
  public BaseMapper<SysJob, String> getMappser() {
    return jobMapper;
  }
}
