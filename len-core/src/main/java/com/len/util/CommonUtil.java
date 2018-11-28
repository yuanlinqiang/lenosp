package com.len.util;


import com.len.base.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email 154040976@qq.com
 *
 * 管理工具类
 */
public class CommonUtil {

  /**
   * 获取当前用户
   */
  public static CurrentUser getUser() {
    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    return (CurrentUser) session.getAttribute("curentUser");
  }
}

  /**
   * 获取权限
   * @return
   */
  /*public static List<SysPermission> getPermission(){
    SysUser user=CommonUtil.getUser();
    if(user!=null){

    }
  }

}
*/