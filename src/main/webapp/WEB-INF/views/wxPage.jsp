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
    <script src="${ctx}/static/js/angular/controller.js"></script>
    <script src="${ctx}/static/js/angular/route.js"></script>
</head>

<body>

<ion-nav-bar class="bar-light">
    <ion-nav-back-button>
    </ion-nav-back-button>
</ion-nav-bar>
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

        <ion-content class="padding">

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
    <ion-view view-title="播放器">
        <ion-content class="padding">
            <h3>Create hybrid mobile apps with the web technologies you love.</h3>
            <p>Free and open source, Ionic offers a library of mobile-optimized HTML, CSS and JS components for building highly interactive apps.</p>
            <p>Built with Sass and optimized for AngularJS.</p>
        </ion-content>
    </ion-view>
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
        </ion-content>
    </ion-view>
</script>




</body>
</html>
