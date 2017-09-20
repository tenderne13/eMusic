<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
    <title>网易云音乐</title>
    <script src="${ctx}/static/js/jquery-1.12.2.js" ></script>
    <script src="${ctx}/static/layer/layer.js" ></script>
    <script src="${ctx}/static/js/aes.js" ></script>
    <script src="${ctx}/static/js/bigint.js" ></script>
    <script src="${ctx}/static/js/lowebutil.js" ></script>
    <script src="${ctx}/static/js/myMusic.js" ></script>
    <script src="${ctx}/static/player/skPlayer2.0.js" ></script>
    <link href="${ctx}/static/css/core.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/pt_frame.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/skPlayer.css" rel="stylesheet"  />

</head>
<script type="text/javascript">
	var player;

	$(function(){
    	netPlayer.showPlayList('hot',0);
	    player = new skPlayer({
		    autoplay: false,
		    music: {
			    type: 'file',
			    source: [
				    //必需项,音乐文件数组
				    {
				    	id:'404783205',
					    name: 'Work from Home',
					    //必需项,歌名
					    author: 'Fifth Harmony/Ty Dolla $ign',
					    //必需项,歌手
					    src: 'xxx.mp3',
					    //必需项,音乐文件
					    cover: 'http://p4.music.126.net/LOEH8DU92vx2GJc0tX1xsA==/109951162971666277.jpg?param=200y200'
					    //必需项,封面图片
				    }
			    ]
		    }
	    });


    });


    //添加歌单回调函数
	function playCallback(playTracks){
		    //var index =$(".skPlayer-curMusic").attr("data-index");
            player.destroy();
            player=new skPlayer({
                    autoplay: false,
                    music: {
                        type: 'file',
                        source: playTracks
                    }
                });

		    //player.switchMusic(index);
    }

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
                <a title="加载更多" href="javaScript:;" class="msk" onclick="netPlayer.loadingMore()">加载更多</a>
            </div>
        </div>
        <div id="skPlayer" style="position: fixed;top: 2px;right:10px;"></div>
    </div>
</body>
</html>
