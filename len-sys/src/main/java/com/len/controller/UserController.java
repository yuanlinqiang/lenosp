package com.len.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.len.base.BaseController;
import com.len.core.annotation.Log;
import com.len.core.annotation.Log.LOG_TYPE;
import com.len.core.quartz.JobTask;
import com.len.entity.SysRoleUser;
import com.len.entity.SysUser;
import com.len.exception.MyException;
import com.len.service.RoleUserService;
import com.len.service.SysUserService;
import com.len.util.*;
import io.swagger.annotations.ApiOperation;

import java.io.*;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhuxiaomeng
 * @date 2017/12/6.
 * @email 154040976@qq.com
 * 用户管理
 */
//@Api(value="user")
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    //private static final Logger

    @Autowired
    SysUserService userService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    JobTask task;

    @GetMapping(value = "mainTest")
    @RequiresPermissions("user:show")
    public String showTest() {
        return "system/user/mainTest";
    }

    @GetMapping(value = "showUser")
    @RequiresPermissions("user:show")
    public String showUser(Model model) {
        return "/system/user/userList";
    }

    @GetMapping(value = "showUserList")
    @ResponseBody
    @RequiresPermissions("user:show")
    public ReType showUser(Model model, SysUser user, String page, String limit) {
        return userService.show(user, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @ApiOperation(value = "/listByRoleId", httpMethod = "GET", notes = "展示角色")
    @GetMapping(value = "listByRoleId")
    @ResponseBody
    @RequiresPermissions("user:show")
    public String showUser(Model model, String roleId,int page, int limit) {
        JSONObject returnValue = new JSONObject();
        List<SysUser> users = userService.getUserByRoleId(roleId,page,limit);
        int counts =  userService.countUserByRoleId(roleId,page,limit);
        returnValue.put("users",users);
        returnValue.put("totals",counts);
        return JSON.toJSONString(returnValue);
    }


    @GetMapping(value = "showAddUser")
    public String goAddUser(Model model) {
        List<Checkbox> checkboxList = userService.getUserRoleByJson(null);
        model.addAttribute("boxJson", checkboxList);
        return "/system/user/add-user";
    }

    @ApiOperation(value = "/addUser", httpMethod = "POST", notes = "添加用户")
    @Log(desc = "添加用户")
    @PostMapping(value = "addUser")
    @ResponseBody
    public JsonUtil addUser(SysUser user, String[] role) {
        if (user == null) {
            return JsonUtil.error("获取数据失败");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return JsonUtil.error("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return JsonUtil.error("密码不能为空");
        }
        if (role == null) {
            return JsonUtil.error("请选择角色");
        }
        int result = userService.checkUser(user.getUsername());
        if (result > 0) {
            return JsonUtil.error("用户名已存在");
        }
        JsonUtil j = new JsonUtil();
        try {
            userService.insertSelective(user);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(user.getId());
            for (String r : role) {
                sysRoleUser.setRoleId(r);
                roleUserService.insertSelective(sysRoleUser);
            }
            j.setMsg("保存成功");
        } catch (MyException e) {
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }

    @GetMapping(value = "updateUser")
    public String goUpdateUser(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            //用户-角色
            List<Checkbox> checkboxList = userService.getUserRoleByJson(id);
            SysUser user = userService.selectByPrimaryKey(id);
            model.addAttribute("user", user);
            model.addAttribute("boxJson", checkboxList);
        }
        model.addAttribute("detail", detail);
        return "system/user/update-user";
    }

    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public JsonUtil updateUser(SysUser user, String role[]) {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.setFlag(false);
        if (user == null) {
            jsonUtil.setMsg("获取数据失败");
            return jsonUtil;
        }
        try {
            SysUser oldUser = userService.selectByPrimaryKey(user.getId());
            BeanUtil.copyNotNullBean(user, oldUser);
            userService.updateByPrimaryKeySelective(oldUser);

            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(oldUser.getId());
            List<SysRoleUser> keyList = userService.selectByCondition(sysRoleUser);
            for (SysRoleUser sysRoleUser1 : keyList) {
                roleUserService.deleteByPrimaryKey(sysRoleUser1);
            }
            if (role != null) {
                for (String r : role) {
                    sysRoleUser.setRoleId(r);
                    roleUserService.insert(sysRoleUser);
                }
            }
            jsonUtil.setFlag(true);
            jsonUtil.setMsg("修改成功");
        } catch (MyException e) {
            e.printStackTrace();
        }
        return jsonUtil;
    }

    @Log(desc = "删除用户", type = LOG_TYPE.DEL)
    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除用户")
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("user:del")
    public JsonUtil del(String id, boolean flag) {
        return userService.delById(id, flag);
    }

    @GetMapping(value = "goRePass")
    public String goRePass(String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            return "获取账户信息失败";
        }
        SysUser user = userService.selectByPrimaryKey(id);
        model.addAttribute("user", user);
        return "/system/user/re-pass";
    }

    /**
     * 修改密码
     *
     * @param id
     * @param pass
     * @param newPwd
     * @return
     */
    @Log(desc = "修改密码", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "rePass")
    @ResponseBody
    @RequiresPermissions("user:repass")
    public JsonUtil rePass(String id, String pass, String newPwd) {
        boolean flag = StringUtils.isEmpty(id) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(newPwd);
        JsonUtil j = new JsonUtil();
        j.setFlag(false);
        if (flag) {
            j.setMsg("获取数据失败，修改失败");
            return j;
        }
        SysUser user = userService.selectByPrimaryKey(id);
        newPwd = Md5Util.getMD5(newPwd, user.getUsername());
        pass = Md5Util.getMD5(pass, user.getUsername());
        if (!pass.equals(user.getPassword())) {
            j.setMsg("密码不正确");
            return j;
        }
        if (newPwd.equals(user.getPassword())) {
            j.setMsg("新密码不能与旧密码相同");

            return j;
        }
        user.setPassword(newPwd);
        try {
            userService.rePass(user);
            j.setMsg("修改成功");
            j.setFlag(true);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return j;
    }

    @Autowired
    UploadUtil uploadUtil;

    /**
     * 头像上传 目前首先相对路径
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public JsonUtil imgUpload(HttpServletRequest req, @RequestParam("file") MultipartFile file,
                              ModelMap model) {
        String fileName=uploadUtil.upload(file);
        JsonUtil j = new JsonUtil();
        j.setMsg(fileName);
        return j;
    }

    /**
     * 验证用户名是否存在
     */
    @GetMapping(value = "checkUser")
    @ResponseBody
    public JsonUtil checkUser(String uname, HttpServletRequest req) {
        JsonUtil j = new JsonUtil();
        j.setFlag(Boolean.FALSE);
        if (StringUtils.isEmpty(uname)) {
            j.setMsg("获取数据失败");
            return j;
        }
        int result = userService.checkUser(uname);
        if (result > 0) {
            j.setMsg("用户名已存在");
            return j;
        }
        j.setFlag(true);
        return j;

    }


}
