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
    <%--<script src="${ctx}/static/js/angular/service.js"></script>--%>
    <script src="${ctx}/static/js/angular/route.js"></script>
</head>

<body>


<ion-nav-view></ion-nav-view>


<script id="templates/tabs.html" type="text/ng-template">
    <ion-tabs class="tabs-icon-top  ">

        <ion-tab title="热门歌单" icon="ion-home" href="#/tab/home">
            <ion-nav-view name="home-tab"></ion-nav-view>
        </ion-tab>

        <ion-tab title="About" icon="ion-social-youtube-outline" href="#/tab/about">
            <ion-nav-view name="about-tab"></ion-nav-view>
        </ion-tab>

        <ion-tab title="Contact" icon="ion-ios-world" ui-sref="tabs.contact">
            <ion-nav-view name="contact-tab"></ion-nav-view>
        </ion-tab>



    </ion-tabs>
</script>

<script id="templates/home.html" type="text/ng-template">
    <ion-view view-title="Home">
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

<script id="templates/facts.html" type="text/ng-template">
    <ion-view view-title="Facts">
        <ion-content class="padding">
            <div class="row row-wrap">
                ceshi
                <div class="col col-25" ng-repeat="item in items">
                    <ul>
                        <li>
                            <img src="img/ionic.png" ng-click="alertClick({{item.text}})">
                            <p>{{item.text}}</p>
                        </li>
                    </ul>
                </div>
            </div>
            <p>Banging your head against a wall uses 150 calories an hour.</p>
            <p>Dogs have four toes on their hind feet, and five on their front feet.</p>
            <p>The ant can lift 50 times its own weight, can pull 30 times its own weight and always falls over on its right side when intoxicated.</p>
            <p>A cockroach will live nine days without it's head, before it starves to death.</p>
            <p>Polar bears are left handed.</p>
            <p>
                <a class="button icon ion-home" href="#/tab/home"> Home</a>
                <a class="button icon icon-right ion-chevron-right" href="#/tab/facts2">More Facts</a>
            </p>
        </ion-content>
    </ion-view>
</script>

<script id="templates/facts2.html" type="text/ng-template">
    <ion-view view-title="Also Factual">
        <ion-content class="padding">
            <p>111,111,111 x 111,111,111 = 12,345,678,987,654,321</p>
            <p>1 in every 4 Americans has appeared on T.V.</p>
            <p>11% of the world is left-handed.</p>
            <p>1 in 8 Americans has worked at a McDonalds restaurant.</p>
            <p>$283,200 is the absolute highest amount of money you can win on Jeopardy.</p>
            <p>101 Dalmatians, Peter Pan, Lady and the Tramp, and Mulan are the only Disney cartoons where both parents are present and don't die throughout the movie.</p>
            <p>
                <a class="button icon ion-home" href="#/tab/home"> Home</a>
                <a class="button icon ion-chevron-left" href="#/tab/facts"> Scientific Facts</a>
            </p>
        </ion-content>
    </ion-view>
</script>

<script id="templates/about.html" type="text/ng-template">
    <ion-view view-title="About">
        <ion-content class="padding">
            <h3>Create hybrid mobile apps with the web technologies you love.</h3>
            <p>Free and open source, Ionic offers a library of mobile-optimized HTML, CSS and JS components for building highly interactive apps.</p>
            <p>Built with Sass and optimized for AngularJS.</p>
            <p>
                <a class="button icon icon-right ion-chevron-right" href="#/tab/navstack">Tabs Nav Stack</a>
            </p>
        </ion-content>
    </ion-view>
</script>

<script id="templates/nav-stack.html" type="text/ng-template">
    <ion-view view-title="Tab Nav Stack">
        <ion-content class="padding">
            <p><img src="http://ionicframework.com/img/diagrams/tabs-nav-stack.png" style="width:100%"></p>
        </ion-content>
    </ion-view>
</script>

<script id="templates/contact.html" type="text/ng-template">
    <ion-view title="Contact">
        <ion-content>
            <div class="list">
                <div class="item">
                    @IonicFramework
                </div>
                <div class="item">
                    @DriftyTeam
                </div>
            </div>
        </ion-content>
    </ion-view>
</script>



<script id="templates/grid.html" type="text/ng-template">
    <ion-view view-title="grid">
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
</body>
</html>
