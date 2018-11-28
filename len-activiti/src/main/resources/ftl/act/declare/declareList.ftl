<#-- Created by IntelliJ IDEA.
 User: zxm
 Date: 2018/1/15
 Time: 16:53
 To change this template use File | Settings | File Templates.
流程部署-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>业绩申报流程</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css"/>
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js" charset="utf-8"></script>
</head>

<body>
<div class="lenos-search">
    <div class="select">
    <#--开始时间：-->
    <#--<div class="layui-inline">-->
    <#--<input class="layui-input"  placeholder="yyyy-MM-dd" height="20px" id="beginTime" autocomplete="off">-->
    <#--</div>-->
    <#--结束时间：-->
    <#--<div class="layui-inline">-->
    <#--<input class="layui-input"  placeholder="yyyy-MM-dd" height="20px" id="endTime" autocomplete="off">-->
    <#--</div>-->
    <#--<button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>-->
    <#--</button>-->
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
    </div>
</div>
<div class="layui-col-md12">
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-normal" data-type="createDeclare">
            <i class="layui-icon">&#xe640;</i>新建业绩申报
        </button>
    </div>
</div>

<table id="delcareList" class="layui-hide" lay-filter="delcare"></table>
<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="getProcImage"><i class="layui-icon">&#xe640;</i>查看流程图</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delcareDetail"><i class="layui-icon">&#xe640;</i>查看审批详情</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delcareBillDetail"><i class="layui-icon">&#xe640;</i>查看单据详情</a>
</script>
<script type="text/html" id="status">
    {{#if(typeof(d.taskName)!='undefined'){}}
    <div>${d.taskName}</div>
    {{# }else{}}
    结束
    {{# }}}
</script>
<script>
    layui.laytpl.toDateString = function (d, format) {
        var date = new Date(d || new Date())
                , ymd = [
            this.digit(date.getFullYear(), 4)
            , this.digit(date.getMonth() + 1)
            , this.digit(date.getDate())
        ]
                , hms = [
            this.digit(date.getHours())
            , this.digit(date.getMinutes())
            , this.digit(date.getSeconds())
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
    layui.laytpl.digit = function (num, length, end) {
        var str = '';
        num = String(num);
        length = length || 2;
        for (var i = num.length; i < length; i++) {
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num | 0) : num;
    };

    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }

    layui.use('table', function () {
        var table = layui.table, laydate = layui.laydate;
        // var a = laydate.render({
        //   elem: '#beginTime',
        //   done: function(value, date, endDate) {
        //     b.config.min = {
        //       year: date.year,
        //       month: date.month - 1,
        //       date: date.date,
        //       hours: date.hours,
        //       minutes: date.minutes,
        //       seconds: date.seconds
        //     }
        //   }
        // });
        // var b = laydate.render({
        //   elem: '#endTime'
        // });
        //方法级渲染
        table.render({
            id: 'delcareList',
            elem: '#delcareList',
            url: 'showDeclareList',
            cols: [[
                {checkbox: true, fixed: true, width: '5%'},
                {field: 'userName', title: '申请人', width: '10%', sort: true},
                {
                    field: 'createDate',
                    title: '创建时间',
                    width: '10%',
                    sort: true,
                    templet: '<div>{{ layui.laytpl.toDateString(d.beginTime,"yyyy-MM-dd") }}</div>'
                },
                {field: 'taskName', title: '状态', width: '10%', templet: '#status'},
                {field: 'reason', title: '原因', width: '10%', sort: true},
                {field: 'processInstanceId', title: '流程定义id', width: '10%', sort: true},
                {field: 'text', title: '操作', width: '35%', toolbar: '#toolBar'}

            ]],
            page: true,
            height: 'full-84'
        });

        var $ = layui.$, active = {
            // select: function () {
            //     var beginTime = $('#beginTime').val();
            //     var endTime = $('#endTime').val();
            //     table.reload('leaveList', {
            //         where: {
            //             beginTime: beginTime,
            //             endTime: endTime
            //         }
            //     });
            // },
            createDeclare: function () {
                add("申请业绩申报", 'addDeclare', 700, 450);
            },
            reload: function () {
                table.reload('delcareList', {
                    where: {

                    }
                });
            }
        };
        //监听工具条
        table.on('tool(delcare)', function (obj) {
            var data = obj.data;
            if (obj.event === 'start') {
                start(data.key);
            } else if (obj.event === 'getProcImage') {
              //  var url = '${re.contextPath}/leave/getShineProcImage?processInstanceId=' + data.processInstanceId + '';
                layer.open({
                    id: 'delcare-image',
                    type: 2,
                    area: ['880px', '400px'],
                    fix: false,
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '流程图',
                    content: '${re.contextPath}/leave/shinePics/' + data.processInstanceId
                });
            } else if (obj.event === 'delcareDetail') {
                layer.open({
                    id: 'delcare-detail',
                    type: 2,
                    area: ['880px', '400px'],
                    fix: false,
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '审核详情',
                    content: "${re.contextPath}/leave/leaveDetail?processId=" + data.processInstanceId
                });
            }else if(obj.event ==='delcareBillDetail')
            {
                layer.open({
                    id: 'delcare-infodetail',
                    type: 2,
                    area: ['880px', '400px'],
                    fix: false,
                    maxmin: true,
                    shadeClose: false,
                    shade: 0.4,
                    title: '单据详情',
                    content:  "readOnlyLeave/"+data.id
                });
            }
        });

        eleClick(active, '.layui-col-md12 .layui-btn');
        eleClick(active, '.select .layui-btn');

    });

    function add(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "404.html";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'delcare-add',
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
