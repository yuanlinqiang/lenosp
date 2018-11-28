<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>更新任务</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/ztree/css/metroStyle/metroStyle.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>
</head>
<script type="text/javascript">
    $(document).ready(function() {
        var flag='${detail}';
        if(flag){
            $("form").disable();
        }
    });
</script>
<body>
<div class="x-body">
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">任务信息</legend>
      </fieldset>
    </div>
    <div class="layui-form-item">
      <label for="jobName" class="layui-form-label">
        <span class="x-red">*</span>任务名称
      </label>
      <div class="layui-input-inline">
        <input type="hidden" value="${job.id}" name="id">
        <input type="text" value="${job.jobName}" id="jobName" name="jobName"  lay-verify="jobName"
               autocomplete="off" class="layui-input">
      </div>
      <div id="ms" class="layui-form-mid layui-word-aux">
        <span class="x-red">*</span><span id="ums">名称必填</span>
      </div>
    </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label for="cron" class="layui-form-label">
        <span class="x-red">*</span>表达式
      </label>
      <div class="layui-input-inline">
        <input type="text" id="cron" name="cron" lay-verify="cron" value="${job.cron}"  autocomplete="off" class="layui-input">
      </div>
      <div id="ms" class="layui-form-mid layui-word-aux">
        <span class="x-red">*</span><a href="http://cron.qqe2.com/" target="_blank">获取表达式</a>
      </div>
    </div>
    </div>
      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">执行类要实现Job</legend>
        </fieldset>
      </div>
      <div class="layui-form-item">
    <div class="layui-inline">
      <label for="clazzPath" class="layui-form-label">
        <span class="x-red">*</span>任务执行类
      </label>
      <div class="layui-input-inline">
        <input type="text" id="clazzPath" style="width: 300px;" value="${job.clazzPath}" name="clazzPath" lay-verify="clazzPath"  autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>
      <div class="layui-form-item">
    <div class="layui-inline">
      <label for="jobDesc" class="layui-form-label">
        <span class="x-red">*</span>任务描述
      </label>
      <div class="layui-input-inline">
        <input type="text" id="jobDesc" name="jobDesc" value="${job.jobDesc}" lay-verify="jobDesc"  autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>
      <div style="height: 60px"></div>
    </div>
<#if !detail>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
    position: fixed;bottom: 1px;margin-left:-20px;">
  <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
        确认
      </button>
      <button  class="layui-btn layui-btn-primary" id="close">
        取消
      </button>
    </div>
  </div>
</#if>
  </form>
</div>
<script>
    layui.use(['form','layer'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer;

    //自定义验证规则
    form.verify({
      jobName: function(value){
        if(value.trim()==""){
          return "任务名称不能为空";
        }
      },
      cron:function(value) {
        if (value.trim() == "") {
          return "表达式不能为空";
        }
      },
        clazzPath:function(value){
        if(value.trim()==""){
          return "执行类不能为空";
        }
      }
    });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交
    form.on('submit(add)', function(data){
      layerAjax('updateJob', data.field, 'jobList');
      return false;
    });
  });
</script>
</body>

</html>
