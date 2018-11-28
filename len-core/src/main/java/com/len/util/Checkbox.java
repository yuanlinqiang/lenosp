package com.len.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuxiaomeng
 * @date 2017/12/21.
 * @email 154040976@qq.com
 * 复选框类
 */
@Getter
@Setter
public class Checkbox {

  private String id;
  private String name;
  /**默认未选择*/
  private boolean check=false;

}
