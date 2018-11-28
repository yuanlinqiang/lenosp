package com.len.validator.group;

import javax.validation.GroupSequence;

/**
 * 
 * @ClassName: Group   
 * @Description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author: liutao
 * @date: 2018年3月16日 下午2:29:21
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
