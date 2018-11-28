<#--Created by IntelliJ IDEA.
User: Administrator
Date: 2017/12/7
Time: 12:40
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>用户管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
</head>

<body>
<div class="x-body">
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
      <legend style="font-size:16px;">头像上传</legend>
    </fieldset>
      <div class="layui-input-inline">
    <div class="layui-upload-drag" style="margin-left:10%;" id="test10">
      <i style="font-size:30px;" class="layui-icon"></i>
     <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
    </div>
      </div>
      <div class="layui-input-inline">

          <div  id="demo2" style="margin-top: 20px;margin-left: 50px">
            <img src="${re.contextPath}/plugin/x-admin/images/bg.png" width="100px" height="100px" class="layui-upload-img layui-circle">
          </div>

      </div>
    </div>
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">基础信息</legend>
      </fieldset>
    </div>
    <div class="layui-form-item">
      <label for="uname" class="layui-form-label">
        <span class="x-red">*</span>用户名
      </label>
      <div class="layui-input-inline">
        <input type="text"  id="uname" name="username"  lay-verify="username"
               autocomplete="off" class="layui-input">
      </div>
      <div id="ms" class="layui-form-mid layui-word-aux">
        <span class="x-red">*</span><span id="ums">将会成为您唯一的登入名</span>
      </div>
    </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label for="realName" class="layui-form-label">
        <span class="x-red">*</span>真实姓名
      </label>
      <div class="layui-input-inline">
        <input type="text" id="realName" name="realName" lay-verify="realName"  autocomplete="off" class="layui-input">
      </div>
    </div>
      <div class="layui-inline">
        <label for="age" class="layui-form-label">
          <span class="x-red">*</span>年龄
        </label>
        <div class="layui-input-inline">
          <input type="text" id="age" name="age"  lay-verify="number"
                 autocomplete="off" class="layui-input">
        </div>
      </div>

    </div>
    <div class="layui-form-item">
      <div class="layui-inline">
      <label for="password" class="layui-form-label">
        <span class="x-red">*</span>密码
      </label>
      <div class="layui-input-inline">
        <input type="password" id="password" name="password"  lay-verify="password"
               autocomplete="off" class="layui-input">
      </div>
      </div>
      <div class="layui-inline">
        <label for="L_repass" class="layui-form-label">
          <span class="x-red">*</span>确认密码
        </label>
        <div class="layui-input-inline">
          <input type="password" id="l_repass" name="repass"  lay-verify="repass"
                 autocomplete="off" class="layui-input">
        </div>
      </div>
    </div>
    <div>
      <label for="email" class="layui-form-label">
        <span class="x-red"></span>邮箱
      </label>
      <div class="layui-input-block">
        <input type="email" id="email" style="width: 93%" name="email"  lay-verify="email"
               autocomplete="off" class="layui-input">
        <input id="photo" name="photo" type="hidden">
      </div>
    </div>
      <div class="layui-form-item">
        <label class="layui-form-label">角色选择</label>
        <div class="layui-input-block">
            <#list boxJson as json>
                <input type="checkbox" name="role" title="${json.name}" lay-filter="check" value="${json.id}">
            </#list>
        </div>
      </div>
      <div style="height: 60px"></div>
    </div>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
        增加
      </button>
      <button  class="layui-btn layui-btn-primary" id="close">
        取消
      </button>
    </div>
  </div>
  </form>
</div>
<script>
  var flag,msg;
  console.info(flag);
  $(function(){
      $('#uname').on("blur",function(){
        var uname=$('#uname').val();
        if(uname.match(/[\u4e00-\u9fa5]/)){
          return;
        }
        if(!/(.+){3,12}$/.test(uname)){
          return;
        }
        if(uname!='') {
          $.ajax({
            url: 'checkUser?uname=' + uname, async: false, type: 'get', success: function (data) {
              console.info(!data.flag);
              flag = data.flag;
              $('#ms').find('span').remove();
              if (!data.flag) {
                msg = data.msg;
                $('#ms').append("<span style='color: red;'>"+data.msg+"</span>");
               // layer.msg(msg,{icon: 5,anim: 6});
              }else{
                flag=true;
                $('#ms').append("<span style='color: green;'>用户名可用</span>");
              }
            },beforeSend:function(){
              $('#ms').find('span').remove();
              $('#ms').append("<span>验证ing</span>");
            }
          });
        }
      });

  });
  layui.use(['form','layer','upload'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer,
        upload = layui.upload;

    upload.render({
      elem: '#test10'
      ,url: 'upload'
      ,before: function(obj){
        //预读，不支持ie8
        obj.preview(function(index, file, result){
          $('#demo2').find('img').remove();
          $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" width="100px" height="100px" class="layui-upload-img layui-circle">');
        });
      },done: function(res){
       if(!res.flag){
         layer.msg(res.msg,{icon: 5,anim: 6});
       }else{
         $("#photo").val(res.msg);
         console.info($('#photo').val());
       }
      }
    });

    //自定义验证规则
    form.verify({
      username: function(value){
        if(value.trim()==""){
          return "用户名不能为空";
        }
        if(value.match(/[\u4e00-\u9fa5]/)){
          return "用户名不能为中文";
        }
        if(!/(.+){3,12}$/.test(value)){
          return "用户名必须3到12位";
        }
        if(typeof(flag)=='undefined'){
          return "用户名验证ing";
        }
        if(!flag){
          return msg;
        }
      }
      ,password: [/(.+){6,12}$/, '密码必须6到12位']
      ,repass: function(value){
        if($('#password').val()!=$('#l_repass').val()){
          return '两次密码不一致';
        }
      }
      ,email:function(value){
        if(value!=""){
          if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)){
            return "邮箱格式不正确";
          }
        }
      }
    });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交
    form.on('submit(add)', function(data){
        var r=document.getElementsByName("role");
        var role=[];
        for(var i=0;i<r.length;i++){
            if(r[i].checked){
                console.info(r[i].value);
                role.push(r[i].value);
            }
        }
        data.field.role=role;
      layerAjax('addUser', data.field, 'userList');
      return false;
    });
    form.render();
  });
</script>
</body>

</html>
