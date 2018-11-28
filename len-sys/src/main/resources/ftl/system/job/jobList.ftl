<#-- Created by IntelliJ IDEA.
 User: zxm
 Date: 2017/1/7
 Time: 11:06
 To change this template use File | Settings | File Templates.-->
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>任务管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
          charset="utf-8"></script>
</head>

<body>
<div class="lenos-search">
  <div class="select">
    任务名称：
    <div class="layui-inline">
      <input class="layui-input" height="20px" id="jobName" autocomplete="off">
    </div>
    描述：
    <div class="layui-inline">
      <input class="layui-input" height="20px" id="remark" autocomplete="off">
    </div>
    <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
    </button>
    <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
            data-type="reload">
      <i class="layui-icon">ဂ</i>
    </button>
  </div>

</div>
<div class="layui-col-md12" style="height:40px;margin-top:3px;">
  <div class="layui-btn-group">
    <@shiro.hasPermission name="job:add">
    <button class="layui-btn layui-btn-normal" data-type="add">
      <i class="layui-icon">&#xe608;</i>新增
    </button>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="job:update">
    <button class="layui-btn layui-btn-normal" data-type="update">
      <i class="layui-icon">&#xe642;</i>编辑
    </button>
   </@shiro.hasPermission>
    <@shiro.hasPermission name="job:select">
    <button class="layui-btn layui-btn-normal" data-type="detail">
      <i class="layui-icon">&#xe605;</i>查看
    </button>
    </@shiro.hasPermission>
  </div>
</div>
<table id="jobList" class="layui-hide" lay-filter="job"></table>
<script type="text/html" id="toolBar">
  <@shiro.hasPermission name="job:add">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
  </@shiro.hasPermission>
<@shiro.hasPermission name="job:update">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
</@shiro.hasPermission>
  {{#  if(!d.status){ }}
  <@shiro.hasPermission name="job:start">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="start">启动</a>
</@shiro.hasPermission>
  {{#  } }}
  {{# if(d.status){ }}
  <@shiro.hasPermission name="job:end">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="end">停止</a>
</@shiro.hasPermission>
  {{#  } }}
<@shiro.hasPermission name="role:del">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</@shiro.hasPermission>
</script>
<script>
  /**前端后期完美后 会进行封装 目前先不处理 精力在后端。。。*/
  layui.laytpl.toDateString = function(d, format){
    var date = new Date(d || new Date())
        ,ymd = [
      this.digit(date.getFullYear(), 4)
      ,this.digit(date.getMonth() + 1)
      ,this.digit(date.getDate())
    ]
        ,hms = [
      this.digit(date.getHours())
      ,this.digit(date.getMinutes())
      ,this.digit(date.getSeconds())
    ];

    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
    .replace(/MM/g, ymd[1])
    .replace(/dd/g, ymd[2])
    .replace(/HH/g, hms[0])
    .replace(/mm/g, hms[1])
    .replace(/ss/g, hms[2]);
  };

  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };

  document.onkeydown = function (e) { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
      $(".select .select-on").click();
    }
  }
  layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
      id: 'jobList',
      elem: '#jobList'
      , url: 'showJobList'
      , cols: [[
        {checkbox: true, fixed: true, width: '5%'}
        , {field: 'jobName', title: '任务名称', width: '10%', sort: true}
        , {field: 'cron', title: '表达式', width: '10%'}
        , {field: 'clazzPath', title: '任务类', width: '20%', sort: true}
        , {field: 'status', title: '状态', width: '10%', sort: true}
        , {field: 'jobDesc', title: '任务描述', width: '10%'}
        , {field: 'createDate', title: '创建时间', width: '10%',templet: '<div>{{ layui.laytpl.toDateString(d.createDate,"yyyy-MM-dd hh:mm:ss") }}</div>'}
        , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
      ]]
      , page: true
      ,  height: 'full-83'
    });

    var $ = layui.$, active = {
      select: function () {
        var jobName = $('#jobName').val();
        var remark = $('#remark').val();
        table.reload('jobList', {
          where: {
              jobName: jobName,
              jobDesc: remark
          }
        });
      },
      reload:function(){
        $('#jobName').val('');
       $('#remark').val('');
        table.reload('jobList', {
          where: {
              jobName: null,
              jobDesc: null
          }
        });
      },
      add: function () {
        add('添加任务', 'showAddJob', 700, 450);
      },
      update: function () {
        var checkStatus = table.checkStatus('jobList')
            , data = checkStatus.data;
        if (data.length != 1) {
          layer.msg('请选择一行编辑', {icon: 5});
          return false;
        }
        if(data[0].status){
          layer.msg('已经启动任务无法更新,请停止后更新',{icon:5,offset: 'rb',area:['200px','100px'],anim:2});
          return false;
        }
        update('编辑任务', 'updateJob?id=' + data[0].id, 700, 450);
      },
      detail: function () {
        var checkStatus = table.checkStatus('jobList')
            , data = checkStatus.data;
        if (data.length != 1) {
          layer.msg('请选择一行查看', {icon: 5});
          return false;
        }
        detail('查看任务信息', 'updateJob?id=' + data[0].id, 700, 450);
      }
    };
    //监听工具条
    table.on('tool(job)', function (obj) {
      var data = obj.data;
      if (obj.event === 'detail') {
        detail('编辑角色', 'updateJob?id=' + data.id, 700, 450);
      } else if (obj.event === 'del') {
        if(!data.status) {
          layer.confirm('确定删除任务[<label style="color: #00AA91;">' + data.jobName + '</label>]?',
              function () {
                del(data.id);
              });
        }else{
          layer.msg('已经启动任务无法更新,请停止后删除',{icon:5,offset: 'rb',area:['200px','100px'],anim:2});
        }
      } else if (obj.event === 'edit') {
        if(!data.status){
          update('编辑任务', 'updateJob?id=' + data.id, 700, 450);
        }else{
          layer.msg('已经启动任务无法更新,请停止后更新',{icon:5,offset: 'rb',area:['200px','100px'],anim:2});
        }
      } else if(obj.event === 'start'){
        layer.confirm('确定开启任务[<label style="color: #00AA91;">' + data.jobName + '</label>]?', function(){
          reqByAjax(data.id,'startJob','jobList');
        });
      } else if(obj.event === 'end'){
        layer.confirm('确定停止任务[<label style="color: #00AA91;">' + data.jobName + '</label>]?', function(){
          reqByAjax(data.id,'endJob','jobList');
        });
      }
    });

    $('.layui-col-md12 .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    $('.select .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });

  });
    function reqByAjax(id,url,tableId){
    $.ajax({
      url: url,
      type: "post",
      data: {id: id},
      success: function (d) {
        if(d.flag){
          layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
          layui.table.reload(tableId);
        }else{
          layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
        }
      }
    });
  }

  function del(id) {
    $.ajax({
      url: "del",
      type: "post",
      data: {id: id},
      success: function (d) {
        if(d.flag){
          layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
          layui.table.reload('jobList');
        }else{
          layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
        }
      }
    });
  }
  function detail(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    if (url == null || url == '') {
      url = "error/404";
    }
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    layer.open({
      id: 'user-detail',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: true,
      shade: 0.4,
      title: title,
      content: url + '&detail=true',
      // btn:['关闭']
    });
  }
  /**
   * 更新用户
   */
  function update(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    if (url == null || url == '') {
      url = "error/404";
    }
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    layer.open({
      id: 'user-update',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: false,
      shade: 0.4,
      title: title,
      content: url + '&detail=false'
    });
  }

  /*弹出层*/
  /*
   参数解释：
   title   标题
   url     请求的url
   id      需要操作的数据id
   w       弹出层宽度（缺省调默认值）
   h       弹出层高度（缺省调默认值）
   */
  function add(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    if (url == null || url == '') {
      url = "error/404";
    }
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    layer.open({
      id: 'job-add',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: false,
      shade: 0.4,
      title: title,
      content: url
    });
  }
</script>
</body>

</html>
