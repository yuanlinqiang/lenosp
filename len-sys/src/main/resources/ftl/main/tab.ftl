<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>KIT ADMIN</title>
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/layui/css/layui.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/font-awesome/css/font-awesome.min.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/app.css" media="all" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/themes/default.css" media="all" id="skin" kit-skin />
</head>
<body class="kit-theme">
<#--动态tab演示  利用 freemarker 宏实现-->
<#macro tree data start end>
  <#if (start=="start")>
  <div class="layui-side layui-bg-black kit-side">
  <div class="layui-side-scroll">
    <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
  <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
  </#if>
  <#list data as child>
    <#if child.children?size gt 0>
      <li class="layui-nav-item">
        <a class="" href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span> ${child.name}</span></a>
        <dl class="layui-nav-child">
        <@tree data=child.children start="" end=""/>
        </dl>
      </li>
    <#else>
      <dd>
        <a href="javascript:;" kit-target data-options="{url:'${child.url}',icon:'${child.ico}',title:'${child.name}',id:'1'}">
          <i class="layui-icon">${child.ico}</i><span> ${child.name}</span></a>
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



<#--<div class="layui-side layui-bg-black kit-side">
  <div class="layui-side-scroll">
    <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
    <!-- 左侧导航区域（可配合layui已有的垂直导航） &ndash;&gt;
    <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
      <li class="layui-nav-item">
        <a class="" href="javascript:;"><i class="fa fa-plug" aria-hidden="true"></i><span> 基本元素</span></a>
        <dl class="layui-nav-child">
          <dd>
            <a href="javascript:;" kit-target data-options="{url:'test.html',icon:'&#xe6c6;',title:'表格',id:'1'}">
              <i class="layui-icon">&#xe6c6;</i><span> 表格</span></a>
          </dd>
          <dd>
            <a href="javascript:;" data-url="form.html" data-icon="fa-user" data-title="表单" kit-target data-id='2'><i class="fa fa-user" aria-hidden="true"></i><span> 表单</span></a>
          </dd>
          <dd>
            <a href="javascript:;" data-url="nav.html" data-icon="&#xe628;" data-title="导航栏" kit-target data-id='3'><i class="layui-icon">&#xe628;</i><span> 导航栏</span></a>
          </dd>
          <dd>
            <a href="javascript:;" data-url="list4.html" data-icon="&#xe614;" data-title="列表四" kit-target data-id='4'><i class="layui-icon">&#xe614;</i><span> 列表四</span></a>
          </dd>
          <dd>
            <a href="javascript:;" kit-target data-options="{url:'https://www.baidu.com',icon:'&#xe658;',title:'百度一下',id:'5'}"><i class="layui-icon">&#xe658;</i><span> 百度一下</span></a>
          </dd>
        </dl>
      </li>

    </ul>
  </div>
</div>-->
<script src="${re.contextPath}/plugin/plugins/layui/layui.js"></script>
<script src="${re.contextPath}/plugin/tools/main.js"></script>
</body>
</html>