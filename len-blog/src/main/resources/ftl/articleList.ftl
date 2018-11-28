<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文章管理</title>
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
        标题：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="title" autocomplete="off">
        </div>
        提要：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="description" autocomplete="off">
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
        <button class="layui-btn layui-btn-normal" data-type="add">
            <i class="layui-icon">&#xe608;</i>新增
        </button>
        <button class="layui-btn layui-btn-normal" data-type="update">
            <i class="layui-icon">&#xe642;</i>编辑
        </button>
        <button class="layui-btn layui-btn-normal" data-type="detail">
            <i class="layui-icon">&#xe605;</i>查看
        </button>
    </div>
</div>
<table id="articleList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/html" id="switchTpl">
    <input type="checkbox" name="sex" lay-skin="switch" lay-text="女|男" lay-filter="sexDemo">
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
        var table = layui.table;
        //方法级渲染
        table.render({
            id: 'articleList',
            elem: '#articleList'
            , url: '/article/showArticleList'
            , cols: [[
                {checkbox: true, fixed: true, width: '5%'}
                , {field: 'title', title: '标题', width: '25%'}
                , {field: 'description', title: '提要', width: '30%'}
                , {
                    field: 'gmtCreated',
                    title: '发布时间',
                    width: '15%',
                    templet: '<div>{{ layui.laytpl.toDateString(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>'
                }
                , {
                    field: 'gmtModified',
                    title: '最后修改时间',
                    width: '15%',
                    templet: '<div>{{ layui.laytpl.toDateString(d.createTime,"yyyy-MM-dd HH:mm:ss") }}</div>'
                }
            ]]
            , page: true,
            height: 'full-83'
        });

        var $ = layui.$, active = {
            select: function () {
                var uname = $('#title').val();
                var email = $('#description').val();
                console.info(title);
                table.reload('articleList', {
                    where: {
                        title: title,
                        description: description
                    }
                });
            },
            reload: function () {
                $('#title').val('');
                $('#description').val('');
                table.reload('articleList', {
                    where: {
                        title: null,
                        description: null
                    }
                });
            },
            add: function () {
                // todo 修改 url
                add('写文章', 'writeArticle', 700, 450);
            },
            update: function () {
                var checkStatus = table.checkStatus('articleList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑,已选 ' + data.length + ' 行', {icon: 5});
                    return false;
                }
                update('编辑文章', 'updateArticle?id=' + data[0].id, 700, 450);
            },
            detail: function () {
                var checkStatus = table.checkStatus('articleList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行查看,已选 ' + data.length + ' 行', {icon: 5});
                    return false;
                }
                // todo 查看文章
                detail('查看文章', 'aaaaa?id=' + data[0].id, 700, 450);
            }
        };

        //监听表格复选框选择
        table.on('checkbox(user)', function (obj) {
            console.log(obj)
        });
        //监听工具条
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                detail('编辑用户', 'updateUser?id=' + data.id, 700, 450);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除用户[<label style="color: #00AA91;">' + data.username + '</label>]?', {
                    btn: ['逻辑删除', '物理删除']
                }, function () {
                    del(data.id, true);
                }, function () {
                    del(data.id, false);
                });
            } else if (obj.event === 'edit') {
                update('编辑用户', 'updateUser?id=' + data.id, 700, 450);
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


    function del(id, flag) {
        $.ajax({
            url: "del",
            type: "post",
            data: {id: id, flag: flag}, async: false,
            success: function (d) {
                if (d.flag) {
                    window.top.layer.msg(d.msg, {icon: 6, offset: 'rb', area: ['120px', '80px'], anim: 2});
                    layui.table.reload('userList');
                } else {
                    window.top.layer.msg(d.msg, {icon: 5, offset: 'rb', area: ['120px', '80px'], anim: 2});
                }
            }, error: function () {
                alert('error');
            }
        });
    }

    function detail(title, url, w, h) {
        var number = 1;
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "error/404";
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
            url = "404.html";
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
            id: 'user-add',
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
