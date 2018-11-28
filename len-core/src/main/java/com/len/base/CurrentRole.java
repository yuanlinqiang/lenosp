package com.len.base;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhuxiaomeng
 * @date 2017/12/30.
 * @email 154040976@qq.com
 */
@Getter
@Setter
@ToString
public class CurrentRole implements Serializable {

    /**  
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String roleName;

    private String remark;

    public CurrentRole(String id, String roleName, String remark) {
        this.id = id;
        this.roleName = roleName;
        this.remark = remark;
    }
}
