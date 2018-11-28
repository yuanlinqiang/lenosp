<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>新建请假</title>
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
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">请假信息</legend>
      </fieldset>
    </div>
    <div class="layui-form-item">
      <label for="beginTime" class="layui-form-label">
        <span class="x-red">*</span>开始时间
      </label>
      <div class="layui-input-inline">
        <input type="text"  id="beginTime" name="beginTime"  lay-verify="beginTime"  placeholder="yyyy-MM-dd"
               autocomplete="off" class="layui-input">
      </div>
    </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label for="endTime" class="layui-form-label">
        <span class="x-red">*</span>结束时间
      </label>
      <div class="layui-input-inline">
        <input type="text" id="endTime" name="endTime" lay-verify="endTime"  placeholder="yyyy-MM-dd"  autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>
      <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
          <legend style="font-size:16px;">原因</legend>
        </fieldset>
      </div>
      <div class="layui-form-item">
    <div class="layui-inline">
      <label for="reason" class="layui-form-label">
        <span class="x-red">*</span>请假原因
      </label>
      <div class="layui-input-inline">
        <input type="text" id="reason" style="width: 300px;" name="reason" lay-verify="reason"  autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>
      <div style="height: 60px"></div>
    </div>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
        申请
      </button>
      <button  class="layui-btn layui-btn-primary"   data-type="close">
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
        ,layer = layui.layer
    ,laydate = layui.laydate;
    var d = new Date();
    var day=d.getFullYear()+"-"+(parseInt(d.getMonth())+1)+'-'+d.getDate();
    console.log(day);
    var $ = layui.$, active = {
      close: function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
      }
    }
    $('.layui-form-item .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    var a = laydate.render({
      elem: '#beginTime',
      min:day,
      done: function(value, date, endDate) {
        b.config.min = {
          year: date.year,
          month: date.month - 1,
          date: date.date,
          hours: date.hours,
          minutes: date.minutes,
          seconds: date.seconds
        }
      }
    });
    var b = laydate.render({
      elem: '#endTime',
      min: '2018-01-20'
    });

    //自定义验证规则
    form.verify({
      beginTime: function(value){
        if(value.trim()==""){
          return "开始时间不能为空";
        }
      },
      endTime:function(value) {
        if (value.trim() == "") {
          return "结束时间不能为空";
        }
      },
      reason:function(value){
        if(value.trim()==""){
          return "请填写请假原因";
        }
      }
    });
   form.on('submit(close)',function (data) {
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   })
    //监听提交
    form.on('submit(add)', function(data){
      $.ajax({
        url:'addLeave',
        type:'post',
        data:data.field,
        async:false, traditional: true,
        success:function(d){
            var index = parent.layer.getFrameIndex(window.name);
            if(d.flag){
              parent.layer.close(index);
              window.parent.layui.table.reload('leaveList');
              window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
            }else{
              layer.msg(d.msg,{icon:5});
            }
        },error:function(){
          layer.alert("请求失败", {icon: 6},function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
          });
        }
      });
      return false;
    });
  });
</script>
</body>

</html>
