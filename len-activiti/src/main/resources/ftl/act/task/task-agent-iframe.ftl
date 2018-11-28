<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>办理任务</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/ztree/css/metroStyle/metroStyle.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js" charset="utf-8"></script>
</head>

<body>
<div class="x-body">

    <div style="width:100%; height:320px;overflow: hidden;">
        <iframe style="width:100%;height:100%;border: none;" src="${leaveUrl}"></iframe>
    </div>

  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:98%;height:80px;overflow: hidden;">
        <input type="hidden" name="taskId" value="${taskId}">
        <div class="layui-form-item layui-form-text">
          <label class="layui-form-label">审批意见</label>
          <div class="layui-input-block">
            <textarea name="opinion" id="opinion" lay-verify="opinion"  class="layui-textarea" style="min-height: 30px;height: 30px"></textarea>
          </div>
        </div>
    </div>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 10px;margin-top: 8px">
      <input name="flag" type="hidden">
      <button  class="layui-btn layui-btn-normal" lay-filter="ok" lay-submit>
        通过
      </button><button  class="layui-btn layui-btn-normal" lay-filter="no" lay-submit>
        不通过
      </button>
      <button  class="layui-btn layui-btn-primary" data-type="close">
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
      opinion: function(value){
        if(value.trim()==""){
          return "审核信息不能为空";
        }
      }
    });
    var $ = layui.$, active = {
      close: function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
      }
    }
    eleClick(active,'.layui-form-item .layui-btn');

    form.on('submit(no)',function(data){
      data.field.flag=false;
      layerAjax('complete',data.field,'taskList');
      return false;
    });

    form.on('submit(ok)', function(data){
      data.field.flag=true;
      layerAjax('complete',data.field,'taskList');
      return false;
    });
    form.render();
  });
</script>
</body>

</html>
