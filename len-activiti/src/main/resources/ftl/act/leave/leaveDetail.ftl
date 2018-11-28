<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>请假审批详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
          charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js" charset="utf-8"></script>
  <style>
    .layui-input {
      height: 30px;
      width: 120px;
    }

    .x-nav {
      padding: 0 20px;
      position: relative;
      z-index: 99;
      border-bottom: 1px solid #e5e5e5;
      height: 32px;
      overflow: hidden;
    }
  </style>
</head>

<body>

<table id="leaveDetail" class="layui-hide" lay-filter="leave"></table>
<script type="text/html" id="flag">
  {{# if(d.flag){ }}
  <span class="layui-badge layui-bg-green">通过</span>

  {{# }else{ }}
  <span class="layui-badge">未通过</span>
  {{#  } }}
</script>
<script>
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

  layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
      id: 'leaveDetail',
      elem: '#leaveDetail'
      , data: ${leaveDetail}
      , cols: [[
        {field: 'taskId', title: '任务编码', width: '20%'}
        ,{field: 'opName', title: '审批人', width: '20%'}
        , {field: 'opinion', title: '审批信息', width: '30%'}
        , {
          field: 'createTime',
          title: '审批时间',
          width: '20%',
          templet: '<div>{{ layui.laytpl.toDateString(d.beginTime,"yyyy-MM-dd") }}</div>'
        }
        , {field: 'flag', title: '是否通过', width: '10%',templet:'#flag'}
      ]]
      , page: false
      , height: 'full-160'
    });
  });
</script>
</body>

</html>
