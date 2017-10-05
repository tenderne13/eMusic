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

</script>
<body>

    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">手机端列表</div>
        <div class="weui-panel__bd">
            <a href="${ctx}/route/wxIndex" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="http://p4.music.126.net/LOEH8DU92vx2GJc0tX1xsA==/109951162971666277.jpg?param=200y200">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">歌单</h4>
                </div>
            </a>
            <a href="${ctx}/route/wxSearch" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="http://p1.music.126.net/czKCGZNnivm1-QBObTARhQ==/109951163034801662.jpg?param=140y140">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">歌曲搜索</h4>
                </div>
            </a>
        </div>
    </div>


    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">pc端列表</div>
        <div class="weui-panel__bd">
            <a href="${ctx}/route/index" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="http://p4.music.126.net/LOEH8DU92vx2GJc0tX1xsA==/109951162971666277.jpg?param=200y200">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">歌单</h4>
                </div>
            </a>
            <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="http://p1.music.126.net/czKCGZNnivm1-QBObTARhQ==/109951163034801662.jpg?param=140y140">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">歌曲搜索</h4>
                </div>
            </a>
        </div>

    </div>

</body>
</html>
