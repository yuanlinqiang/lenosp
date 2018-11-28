<#--Created by IntelliJ IDEA.
User: Administrator
Date: 2017/12/7
Time: 12:40
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>重置密码</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
</head>

<body>
<div class="x-body">
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:300px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">修改账户：${user.username}</legend>
      </fieldset>
    </div>
    <div class="layui-form-item">
      <label for="pass" class="layui-form-label">
        <span class="x-red">*</span>原密码
      </label>
      <div class="layui-input-inline">
        <input type="hidden" value="${user.id}" name="id">
        <input type="password"  id="pass" name="pass"  lay-verify="pass"
               autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label for="newPass" class="layui-form-label">
        <span class="x-red">*</span>新密码
      </label>
      <div class="layui-input-inline">
        <input type="password" id="newPass" name="newPwd" lay-verify="newPass"  autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
      <label for="reNewPass" class="layui-form-label">
        <span class="x-red">*</span>确认密码
      </label>
      <div class="layui-input-inline">
        <input type="password" id="reNewPass" name="reNewPass"  lay-verify="reNewPass"
               autocomplete="off" class="layui-input">
      </div>
    </div>
      <div style="height: 60px"></div>
    </div>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
        修改
      </button>
      <button  class="layui-btn layui-btn-primary" id="close">
        取消
      </button>
    </div>
  </div>
  </form>
</div>
<script>
  layui.use(['form','layer'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer;
    //自定义验证规则
    form.verify({
      pass: function(value){
        if(value.trim()==""){
          return "密码不能为空";
        }
      }
      ,newPass: [/(.+){6,12}$/, '密码必须6到12位']
      ,reNewPass: function(value){
        if($('#newPass').val()!=$('#reNewPass').val()){
          return '两次密码不一致';
        }
      }
    });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交
    form.on('submit(add)', function(data){
      $.ajax({
        url:'rePass',
        type:'post',
        data:data.field,
        async:false, dataType: "json", traditional: true,
        success:function(json){
          var index = parent.layer.getFrameIndex(window.name);
          parent.layer.close(index);
          window.parent.layui.table.reload('userList');
          window.top.layer.msg(json.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
        },error:function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
          window.top.layer.msg('请求失败',{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
        }
      });
      return false;
    });
    form.render();
  });
</script>
</body>

</html>
