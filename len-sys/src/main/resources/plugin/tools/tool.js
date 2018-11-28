

//通用
function popup(title, url, w, h,id) {
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
    id: id,
    type: 2,
    area: [w + 'px', h + 'px'],
    fix: false,
    maxmin: true,
    shadeClose: true,
    shade: 0.4,
    title: title,
    content: url
  });
}

/**
 * 父窗口弹出
 * @param url
 * @param data
 * @param tableId
 */
function postAjaxre(url,data,tableId){
  $.ajax({
    url: url,
    type: "post",
    data:data,
    dataType: "json", traditional: true,
    success: function (data) {
      if(data.flag){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        window.parent.layui.table.reload(tableId);
        window.top.layer.msg(data.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
      }else{
        layer.msg(data.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
      }
    }
  });
}

function layerAjax(url,data,tableId){
  $.ajax({
    url:url,
    type:'post',
    data:data,
    traditional: true,
    success:function(d){
      var index = parent.layer.getFrameIndex(window.name);
      if(d.flag){
        parent.layer.close(index);
        window.parent.layui.table.reload(tableId);
        window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['200px','80px'],anim:2});
      }else{
        layer.msg(d.msg,{icon:5});
      }
    },error:function(e){
      layer.alert("发生错误", {icon: 6},function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
      });
    }
  });
}

function eleClick(active,ele){
  $(ele).on('click', function () {
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
}

function toolDelByFlag(id,list, flag) {
  var data={id:id};
  if(flag!=null){
    data.flag=flag;
  }
    $.ajax({
        url:"del",
        type:"post",
        data:data,
        success:function(d){
            if(d.flag){
                window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                layui.table.reload(list);
            }else{
                window.top.layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
            }
        },error:function(){
            alert('error');
        }
    });
}
function toolDel(id, list) {
    toolDelByFlag(id,list,null);
}