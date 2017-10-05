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
    <style type="text/css">
        #search{
            padding-left: 15px;
            padding-right: 15px;
        }

    </style>
</head>
<script type="text/javascript">

</script>
<body>
<div class="weui-cells weui-cells_form">

    <div class="weui-cell">
        <div class="weui-cell__bd">
            <input class="weui-input" type="text" placeholder="请输入歌曲名称" id="keyword">
        </div>
    </div>

    <div id="search">
        <a href="javascript:;" class="weui-btn weui-btn_primary" onclick="songSearch()">搜索</a>
    </div>

</div>

<div class="weui-panel weui-panel_access">
    <%--<div class="weui-panel__hd">图文组合列表</div>--%>
    <div class="weui-panel__bd" id="result_content">

    </div>
</div>

<script type="text/javascript">
    //搜索方法
    function songSearch(){
        var keyword=$("#keyword").val();
        if(keyword.trim()=='' || keyword==null){
        	layer.msg("不能为空");
        	return;
        }
        var loading=layer.load(1, {shadeClose: true, shade: 0.4});
        $.post(
        	"${ctx}/api/songSearch",{
        		keyword:keyword
            },function (data) {
                data=JSON.parse(data);
                var songs=data.result.songs;
                var arr=[];
                for(var i=0;i<songs.length;i++){
                	var imgUrl=songs[i].album.blurPicUrl;
                	var name=songs[i].name;
                	var author=songs[i].artists[0].name;
                	var id=songs[i].id;

                    var str='<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" onclick="getOperation(\'' + id + '\',\'' + name + '\')">\n' +
	                    '            <div class="weui-media-box__hd">\n' +
	                    '                <img class="weui-media-box__thumb" src="'+imgUrl+'">\n' +
	                    '            </div>\n' +
	                    '            <div class="weui-media-box__bd">\n' +
	                    '                <h4 class="weui-media-box__title">'+name+'</h4>\n' +
	                    '                <p class="weui-media-box__desc">'+author+'</p>\n' +
	                    '            </div>\n' +
	                    '        </a>';
                    arr.push(str);
                }

                $("#result_content").empty();
                $("#result_content").append(arr);
                layer.close(loading);
	        }
        );
    }



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
                        id='netrack_'+id;
	                    $.toptip('友情提示:下载功能只支持Android手机~', 'warning');
	                    netPlayer.download(id,title);
                    }
                }
			    ]
	    });
    }

</script>
</body>
</html>
