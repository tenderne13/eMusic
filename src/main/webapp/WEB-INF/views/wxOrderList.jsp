<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>网易云音乐</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${ctx}/static/layer/layer.js"></script>
    <script src="${ctx}/static/js/lowebutil.js"></script>
    <script src="${ctx}/static/js/wxMusic.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
    <script src="https://cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>

</head>
<script type="text/javascript">
	$(function () {
		var id = '${id}';
		netPlayer.playList(id);
	});

</script>
<body>
<div class="weui-panel weui-panel_access" id="header">

</div>
<div class="weui-cells" id="orderList">

</div>
<div>
    <a href="javascript:;" class="weui-btn weui-btn_primary" onclick="backToIndex()">返回</a>
</div>


<script type="text/javascript">
    function backToIndex(){
	    window.location.href=netPlayer.params.root + '/route/wxIndex';
    }

    function getOperation(id,title){
	    $.actions({
		    actions: [
			    {
				    text: "播放",
				    className: "color-primary",
                    onClick:function(){
                        $.alert("嘿嘿这个功能我还没做","歌曲:"+title);
                    }
			    },
                {
                	text: "下载",
                    className: "color-warning",
                    onClick:function(){
	                    //$.alert(title,"歌曲id:"+id);
	                    $.toptip('友情提示:下载功能只支持Android手机~', 'warning');
	                    $.toast("资源加载中...", "text");
	                    netPlayer.download(id,title);
                    }
                }
			    ]
	    });
    }

</script>
</body>
</html>
