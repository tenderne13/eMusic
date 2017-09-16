<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
    <title>网易云音乐</title>
    <script src="${ctx}/static/js/jquery-1.12.2.js" ></script>
    <script src="${ctx}/static/js/aes.js" ></script>
    <script src="${ctx}/static/js/bigint.js" ></script>
    <script src="${ctx}/static/js/lowebutil.js" ></script>
    <script src="${ctx}/static/js/myMusic.js" ></script>
    <link href="${ctx}/static/css/core.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/pt_frame.css" rel="stylesheet"  />

</head>
<script type="text/javascript">
    $(function(){
    	var list = netPlayer.showPlayList('hot',0);
    	netPlayer.listAppend(list.result);
    });

</script>
<body>

    <div class="g-bd" id="m-disc-pl-c">
        <div class="g-wrap p-pl f-pr">
            <div class="u-title f-cb">
                <h3><span class="f-ff2 d-flag">全部</span></h3>
            </div>
            <ul class="m-cvrlst f-cb" id="music-container">

            </ul>
            <div id="m-pl-pager">

            </div>
        </div>
    </div>
</body>
</html>
