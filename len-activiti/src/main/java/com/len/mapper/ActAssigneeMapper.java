package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.ActAssignee;
import tk.mybatis.mapper.common.Mapper;

public interface ActAssigneeMapper extends BaseMapper<ActAssignee,String> {
    int deleteByNodeId(String nodeId);
}