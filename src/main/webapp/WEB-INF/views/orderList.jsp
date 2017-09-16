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
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/player.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/cover.css" rel="stylesheet"  />

</head>
<script type="text/javascript">
    $(function(){
    	//netPlayer.showPlayList('hot',0);
        var id='${id}';
        var list = netPlayer.playList(id);
        console.log(list);
    });

</script>
<body>

    <div class="site-wrapper" ng-hide="is_window_hidden==1">
        <div class="site-wrapper-innerd" resize="" style="height: 100%">
            <div class="cover-container">
                <div class="playlist-detail">
                    <div class="detail-head">
                        <div class="detail-head-cover">
                            <img src="http://p4.music.126.net/jbdUMhh4UzXWZ3EsYFKG8A==/19154592067711704.jpg?param=200y200">
                        </div>
                        <div class="detail-head-title">
                            <h2 class="ng-binding">「欧美纯音」炎炎夏日，放松一下疲惫的身心</h2>
                            <a title="播放歌单" class="play" ng-show="playlist_title!=''" ng-click="playMylist(list_id)">播放</a>
                            <%--<a title="添加到当前播放" class="add" ng-show="playlist_title!=''" ng-click="addMylist(list_id)">添加到当前播放</a>
                            <a title="收藏歌单" class="clone ng-hide" ng-show="playlist_title!='' &amp;&amp; !is_mine" ng-click="clonePlaylist(list_id)">收藏</a>
                            <a title="编辑歌单" class="edit" ng-show="playlist_title!='' &amp;&amp; is_mine" ng-click="showDialog(3, {list_id: list_id, playlist_title: playlist_title, cover_img_url: cover_img_url})">编辑</a>--%>
                            <a title="原始链接" class="link ng-isolate-scope" ng-show="playlist_title!=''" open-url="playlist_source_url">原始链接</a>
                        </div>
                    </div>
                    <ul class="detail-songlist">
                        <li ng-repeat="song in songs" ng-class-odd="'odd'" ng-class-even="'even'" ng-mouseenter="options=true" ng-mouseleave="options=false" class="ng-scope odd">
                        <div class="col2">
                            <!-- ngIf: song.disabled -->
                            <!-- ngIf: !song.disabled --><a ng-if="!song.disabled" add-and-play="song" class="ng-binding ng-scope ng-isolate-scope">Hello Summer (Radio Edit)</a><!-- end ngIf: !song.disabled -->
                        </div>
                        <div class="col1 detail-artist"><a ng-click="showPlaylist(song.artist_id)" class="ng-binding">Rameez</a></div>
                        <div class="col2"><a ng-click="showPlaylist(song.album_id)" class="ng-binding">Hello Summer</a></div>
                        <div class="detail-tools">
                            <a title="添加到当前播放" class="detail-add-button ng-isolate-scope ng-hide" add-without-play="song" ng-show="options"></a>
                            <a title="下载" class="detail-fav-button ng-hide" ></a>
                            <a title="原始链接" class="source-button ng-isolate-scope ng-hide" open-url="song.source_url" ng-show="options"></a>

                        </div>
                    </li><!-- end ngRepeat: song in songs --><li ng-repeat="song in songs" ng-class-odd="'odd'" ng-class-even="'even'" ng-mouseenter="options=true" ng-mouseleave="options=false" class="ng-scope even">
                        <div class="col2">
                            <!-- ngIf: song.disabled -->
                            <!-- ngIf: !song.disabled --><a ng-if="!song.disabled" add-and-play="song" class="ng-binding ng-scope ng-isolate-scope">Oops</a><!-- end ngIf: !song.disabled -->
                        </div>
                        <div class="col1 detail-artist"><a ng-click="showPlaylist(song.artist_id)" class="ng-binding">Little Mix</a></div>
                        <div class="col2"><a ng-click="showPlaylist(song.album_id)" class="ng-binding">Glory Days (Deluxe Concert Film Edition)</a></div>
                        <div class="detail-tools">
                            <a title="添加到当前播放" class="detail-add-button ng-isolate-scope ng-hide" add-without-play="song" ng-show="options"></a>
                            <a title="添加到歌单" class="detail-fav-button ng-hide" ng-click="showDialog(0, song)" ng-show="options"></a>
                            <a title="从歌单删除" class="detail-delete-button ng-hide" ng-click="removeSongFromPlaylist(song, list_id)" ng-show="options &amp;&amp; is_mine=='1' "></a>
                            <a title="原始链接" class="source-button ng-isolate-scope ng-hide" open-url="song.source_url" ng-show="options"></a>

                        </div>
                    </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
