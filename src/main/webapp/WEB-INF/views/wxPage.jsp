<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html ng-app="ionicApp">
<head>
    <title>网易云音乐</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width"/>
    <link href="https://cdn.bootcss.com/ionic/1.3.2/css/ionic.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${ctx}/static/layer/layer.js"></script>
    <script src="${ctx}/static/js/lowebutil.js"></script>
    <script src="https://cdn.bootcss.com/ionic/1.3.2/js/ionic.bundle.min.js"></script>
    <%--<link href="http://code.ionicframework.com/nightly/css/ionic.css" rel="stylesheet">
    <script src="http://code.ionicframework.com/nightly/js/ionic.bundle.js"></script>--%>
    <script src="${ctx}/static/js/angular/player/angular-soundmanager2.js"></script>
    <script src="${ctx}/static/js/angular/player/netease.js"></script>
    <script src="${ctx}/static/js/angular/player/loweb.js"></script>
    <script src="${ctx}/static/js/angular/controller.js"></script>
    <script src="${ctx}/static/js/angular/route.js"></script>
</head>
<body>

<%--<ion-nav-bar class="bar-light">
    <ion-nav-back-button>
    </ion-nav-back-button>
</ion-nav-bar>--%>
<ion-nav-view></ion-nav-view>


<script id="templates/tabs.html" type="text/ng-template">
    <ion-tabs class="tabs-icon-top">

        <ion-tab title="热门歌单" icon="ion-home" href="#/tab/home">
            <ion-nav-view name="home-tab"></ion-nav-view>
        </ion-tab>

        <ion-tab title="播放器" icon="ion-social-youtube-outline" href="#/tab/music">
            <ion-nav-view name="music-tab"></ion-nav-view>
        </ion-tab>

        <ion-tab title="搜索" icon="ion-ios-search" ui-sref="tabs.search">
            <ion-nav-view name="search-tab"></ion-nav-view>
        </ion-tab>



    </ion-tabs>
</script>

<script id="templates/home.html" type="text/ng-template">
    <ion-view view-title="热门歌单">

        <ion-content>

            <div class="list card" ng-repeat="item in musicList">
                <a href="javaScript:;" ng-click="getAlbumDetail('{{item.id}}')" class="item item-thumbnail-left">

                    <img ng-src="{{ item.cover_img_url }}">
                    <h2>{{ item.title }}</h2>
                    <p></p>
                    <p>{{ item.author }}</p>
                </a>
            </div>
            <ion-refresher
                    pulling-text="下拉刷新..."
                    on-refresh="doRefresh()">
            </ion-refresher>

            <ion-infinite-scroll
                    on-infinite="loadMore()"
                    distance="1%">
            </ion-infinite-scroll>

        </ion-content>
    </ion-view>
</script>




<script id="templates/grid.html" type="text/ng-template">
    <ion-view view-title="{{title}}">

        <ion-header-bar class="bar-light">
            <div class="buttons">
                <a href="#/tab/home" class="button icon-left ion-chevron-left button-clear button-dark">返回</a>
            </div>
            <h1 class="title">{{title}}</h1>
        </ion-header-bar>


        <ion-content>
            <div class="list card" ng-repeat="item in songList">
                <a href="javaScript:;" ng-click="getSongOperation(item)" class="item item-thumbnail-left">
                    <img ng-src="{{ item.img_url }}">
                    <h2>{{ item.title }}</h2>
                    <p>{{ item.artist }}</p>
                </a>
            </div>
        </ion-content>
    </ion-view>
</script>



<script id="templates/music.html" type="text/ng-template">
    <ion-view view-title="{{title}}">


        <ion-header-bar class="bar-light">
            <div class="buttons">
                <button class="button button-icon icon ion-ios-minus-outline" ng-click="delete.showDelete = !delete.showDelete;"></button>
            </div>
            <h1 class="title">{{title}}</h1>
            <div class="buttons">
                <button class="button button-icon icon ion-stats-bars" ng-show="isPlaying"  ng-click="modal.show()"></button>
            </div>
        </ion-header-bar>


        <ion-content [padding='false']>
            <sound-manager></sound-manager>
            <ion-list show-delete="delete.showDelete" show-reorder="delete.showDelete">
                <ion-item class="" ng-repeat="item in songs track by $index">
                    <a href="javaScript:;" ng-click="isSelected(item)" class="item item-avatar" play-from-playlist="item">
                        <img ng-src="{{ item.img_url }}">
                        <h2>{{ item.title }}</h2>
                        <p>{{ item.artist }}</p>
                    </a>
                    <ion-delete-button class="ion-ios-trash-outline"
                                       ng-click="onSongDelete(item)">
                    </ion-delete-button>
                </ion-item>
            </ion-list>
        </ion-content>
    </ion-view>
</script>

<script id="templates/progress.html" type="text/ng-template">
    <ion-modal-view>
        <ion-header-bar class="bar bar-header bar-light">
            <h1 class="title">{{ title }}</h1>
            <button class="button button-clear button-primary" ng-click="modal.hide()">Back</button>
        </ion-header-bar>
        <ion-content>
            <div class="list card">
                <div class="item item-avatar">
                    <img ng-src="{{currentSong.img_url}}">
                    <h2>{{currentSong.title}}</h2>
                    <p>{{currentSong.artist}}</p>
                </div>
                <div class="item item-image">
                    <img ng-src="{{currentSong.img_url}}">
                </div>
                <div class="item range range-positive">

                </div>
                <div class="item  assertive">
                    <p>{{ currentPosition }}/{{ currentDuration }}</p>
                </div>
                <div class="button-bar button-assertive">
                    <i ng-class="{ 'button icon ion-information': settings.playmode == 2,'button icon ion-ios-shuffle-strong': settings.playmode == 1, 'button icon ion-loop': settings.playmode == 0 }" ng-click="changePlaymode()"></i>
                    <i class="button icon ion-ios-skipbackward" prev-track></i>
                    <i ng-class="{true: 'button  icon ion-pause', false: 'button  icon ion-play'}[isPlaying]" play-pause-toggle></i>
                    <i class="button icon ion-ios-skipforward" next-track></i>
                    <i class="button icon ion-stop" stop-music></i>
                </div>
            </div>
        </ion-content>
    </ion-modal-view>
</script>





<script id="templates/search.html" type="text/ng-template">
    <ion-view title="搜索">
        <ion-content>
            <div class="item item-input-inset">
                <label class="item-input-wrapper">
                    <input type="text" ng-model="keyword" placeholder="歌曲/歌手">
                </label>
                <button class="button button-small button-positive" ng-click="search(keyword)">
                    搜索
                </button>
            </div>
            <div class="list card" ng-repeat="item in songList">
                <a href="javaScript:;" ng-click="getSongOperation(item)" class="item item-thumbnail-left">
                    <img ng-src="{{ item.album.blurPicUrl }}">
                    <h2>{{ item.name }}</h2>
                    <p>{{ item.artists[0].name }}</p>
                </a>
            </div>

            <ion-infinite-scroll
                    ng-if="hasMore"
                    on-infinite="loadMore(keyword)"
                    distance="1%">
            </ion-infinite-scroll>
        </ion-content>
    </ion-view>
</script>


</body>
</html>
