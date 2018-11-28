<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>len-脚手架</title>
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/layui/css/layui.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/font-awesome/css/font-awesome.min.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/app.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/themes/default.css" media="all" id="skin" kit-skin />
  <style>
    <#--前端无聊美化ing-->
    .layui-footer{background-color: #2F4056;}
    .layui-side-scroll{border-right: 3px solid #009688;}
  </style>
</head>

<body class="kit-theme">
<div class="layui-layout layui-layout-admin kit-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">len-脚手架</div>
    <div class="layui-logo kit-logo-mobile"></div>
    <div class="layui-hide-xs">
    <ul class="layui-nav layui-layout-left kit-nav">
      <li class="layui-nav-item"><a href="javascript:s();">会员管理</a></li>
        <li class="layui-nav-item"><a href="javascript:;" kit-target data-options="{url:'/article/articleList',icon:'&#xe658;',title:'文章管理',id:'966'}">文章管理</a></li>
      <#--<li class="layui-nav-item"><a href="javascript:;" id="pay"><i class="fa fa-gratipay" aria-hidden="true"></i> 捐赠我</a></li>-->
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:;">邮件管理</a></dd>
          <dd><a href="javascript:;">消息管理</a></dd>
          <dd><a href="javascript:;">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    </div>
    <ul class="layui-nav layui-layout-right kit-nav">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <i class="layui-icon">&#xe63f;</i> 皮肤</a>
        </a>
        <dl class="layui-nav-child skin">
          <dd><a href="javascript:;" data-skin="default" style="color:#393D49;"><i class="layui-icon">&#xe658;</i> 默认</a></dd>
          <dd><a href="javascript:;" data-skin="orange" style="color:#ff6700;"><i class="layui-icon">&#xe658;</i> 橘子橙</a></dd>
          <dd><a href="javascript:;" data-skin="green" style="color:#00a65a;"><i class="layui-icon">&#xe658;</i> 春天绿</a></dd>
          <dd><a href="javascript:;" data-skin="pink" style="color:#FA6086;"><i class="layui-icon">&#xe658;</i> 少女粉</a></dd>
          <dd><a href="javascript:;" data-skin="blue.1" style="color:#00c0ef;"><i class="layui-icon">&#xe658;</i> 天空蓝</a></dd>
          <dd><a href="javascript:;" data-skin="red" style="color:#dd4b39;"><i class="layui-icon">&#xe658;</i> 枫叶红</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item">
        <a href="javascript:;">
        <#assign currentUser = Session["curentUser"]>
          <img src="${re.contextPath}/images/${currentUser.photo}" class="layui-nav-img">${currentUser.username}
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:;" kit-target data-options="{url:'basic.html',icon:'&#xe658;',title:'基本资料',id:'966'}"><span>基本资料</span></a></dd>
          <dd><a href="javascript:;">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
    </ul>
  </div>

<#macro tree data start end>
  <#if (start=="start")>
  <div class="layui-side layui-nav-tree layui-bg-black kit-side">
  <div class="layui-side-scroll">
    <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
  <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
  </#if>
  <#list data as child>
    <#if child.children?size gt 0>
      <li class="layui-nav-item">
        <a class="" href="javascript:;"><i aria-hidden="true" class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
        <dl class="layui-nav-child">
          <@tree data=child.children start="" end=""/>
        </dl>
      </li>
    <#else>
      <dd>
        <a href="javascript:;" kit-target data-options="{url:'${child.url}',icon:'${child.icon}',title:'${child.name}',id:'${child.num?c}'}">
          <i class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
      </dd>
    </#if>
  </#list>
  <#if (end=="end")>
  </ul>
  </div>
  </div>
  </#if>
</#macro>
<@tree data=menu start="start" end="end"/>
  <div class="layui-body" <#--style="border:1px solid red;padding-bottom:0;"--> id="container">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i> 请稍等...</div>
  </div>

  <div class="layui-footer">
  <!-- 底部固定区域 -->
  2017 &copy;
  <a target="_blank" href="https://my.oschina.net/u/3312115/blog">开源中国博客</a> 由zxm倾心打造的一款快速开发脚手架 <#--技术交流请联系本人-154040976@qq.com-->
</div>
</div>
<script src="${re.contextPath}/plugin/plugins/layui/layui.js"></script>
<script src="${re.contextPath}/plugin/tools/main.js"></script>
</body>

</html>
