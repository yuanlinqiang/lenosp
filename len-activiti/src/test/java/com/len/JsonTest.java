package com.len;

import com.len.entity.UserLeave;
import com.len.util.ReType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * @author zhuxiaomeng
 * @date 2018/1/26.
 * @email 154040976@qq.com
 */
public class JsonTest {
  @Test
  public void json(){
    List<UserLeave> leaves=new ArrayList<>();
    UserLeave leave=new UserLeave();
    /**我们模拟下数据*/
    leave.setId("1");
    leave.setTaskName("45151");
    leave.setProcessInstanceId("54521");
    leaves.add(leave);
    ReType reType=new ReType();
    Map<String, Map<String,Object>> map=new HashMap<>();
    Map<String,Object> map1=new HashMap<>();
    //我们要向UsereLeave对象中添加的 属性名 属性值
    map1.put("aaa","aaaa");
    // 当然也可以是其他的key 1为上面id的值
    map.put("1",map1);
    //node 绑定属性的id
   String s= reType.jsonStrng(5,leaves,map,"id");
   System.out.println(s);
  }
}
