<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
    <title>网易云音乐</title>
    <script src="${ctx}/static/js/jquery-1.12.2.js" ></script>
    <script src="${ctx}/static/layer/layer.js" ></script>
    <script src="${ctx}/static/js/down.js" ></script>
    <script src="${ctx}/static/js/lowebutil.js" ></script>
    <script src="${ctx}/static/js/myMusic.js" ></script>
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/player.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/cover.css" rel="stylesheet"  />


    <script src="${ctx}/static/player/skPlayer2.0.js" ></script>
    <link href="${ctx}/static/css/skPlayer.css" rel="stylesheet"  />

</head>
<script type="text/javascript">
	$(function(){
		var id='${id}';
		netPlayer.playList(id);
	});

</script>
<body>

<div class="site-wrapper">
    <div class="site-wrapper-innerd"  style="height: 100%">
        <div class="cover-container">
            <div class="playlist-detail">
                <div class="detail-head" id="order">

                </div>
                <ul class="detail-songlist" id="orderList">

                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
