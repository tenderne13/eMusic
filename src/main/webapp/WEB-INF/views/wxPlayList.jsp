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
		netPlayer.showPlayList('hot', 0);
	});
</script>
<body>

<div class="weui-grids" id="music-container">

</div>

<div class="weui-loadmore" id="loading" >
    <i class="weui-loading"></i>
    <span class="weui-loadmore__tips">正在加载</span>
</div>

<script type="text/javascript">
	var loading = false;  //状态标记
	$(document.body).infinite().on("infinite", function() {
		if(loading) return;
		loading = true;
		netPlayer.loadingMore();
	});
</script>
<script src="https://cdn.bootcss.com/jquery-weui/1.0.1/js/swiper.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.0.1/js/city-picker.min.js"></script>
</body>
</html>
