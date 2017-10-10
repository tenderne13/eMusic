<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html ng-app="ionicApp">
<head>
    <title>网易云音乐</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width"/>
    <style type="text/css">
        /* 栅格grid */
        .col-25{border: 1px solid #ddd;height: 120px;display:flex;justify-content:center;align-items: center;}
        .col-25 img {height: 64px; width: 64px;}
        .col-25 ul li img {height: 64px; width: 64px;margin-top: 12px}
        .col-25 ul li p {text-align: center;}
    </style>
    <link href="https://cdn.bootcss.com/ionic/1.3.2/css/ionic.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/ionic/1.3.2/js/ionic.bundle.min.js"></script>
<script type="text/javascript">
    angular.module('ionicApp', ['ionic'])

        .config(function($stateProvider, $urlRouterProvider) {

            $stateProvider
                .state('tabs', {
                    url: "/tab",
                    abstract: true,
                    templateUrl: "templates/tabs.html"
                })
                .state('tabs.home', {
                    url: "/home",
                    views: {
                        'home-tab': {
                            templateUrl: "templates/home.html",
                            controller: 'HomeTabCtrl'
                        }
                    }
                })
                .state('tabs.grid', {
                    url: "/grid",
                    views: {
                        'home-tab': {
                            templateUrl: "templates/grid.html",
                            controller: 'GridTabCtrl'
                        }
                    }
                })
                .state('tabs.facts', {
                    url: "/facts",
                    views: {
                        'home-tab': {
                            templateUrl: "templates/facts.html"
                        }
                    }
                })
                .state('tabs.facts2', {
                    url: "/facts2",
                    views: {
                        'home-tab': {
                            templateUrl: "templates/facts2.html"
                        }
                    }
                })
                .state('tabs.about', {
                    url: "/about",
                    views: {
                        'about-tab': {
                            templateUrl: "templates/about.html"
                        }
                    }
                })
                .state('tabs.navstack', {
                    url: "/navstack",
                    views: {
                        'about-tab': {
                            templateUrl: "templates/nav-stack.html"
                        }
                    }
                })
                .state('tabs.contact', {
                    url: "/contact",
                    views: {
                        'contact-tab': {
                            templateUrl: "templates/contact.html"
                        }
                    }
                });
            $urlRouterProvider.otherwise("/tab/home");

        })
        .controller('GridTabCtrl',function($scope,$timeout, $ionicLoading){
            $scope.items=[];
            $ionicLoading.show({
                content: 'Loading',
                animation: 'fade-in',
                showBackdrop: true,
                maxWidth: 200,
                showDelay: 0
            });

            $timeout(function () {
                $ionicLoading.hide();
                $scope.items = [
                    { id: 1, text: "我爱我家哦" }, { id: 2, text: "瓦达瓦大" }, { id: 3, text: "333333" },
                    { id: 4, text: "444444" }, { id: 5, text: "555555" }, { id: 6, text: "阿瓦打我的6" },
                    { id: 7, text: "777777" }, { id: 8, text: "888888" }, { id: 9, text: "爱我的爱我的9999" },
                    { id: 10, text: "aaaaaa" }, { id: 11, text: "bbbbbb" }, { id: 12, text: "cccccc" },
                ];
            }, 2000);

            $scope.alertClick = function (val) {
                alert(val);
            }
        })
        .controller('HomeTabCtrl', function($scope) {
            console.log('HomeTabCtrl');
        });
</script>
</head>

<body>
<%--<ion-nav-bar class="bar-positive">
    <ion-nav-back-button>
    </ion-nav-back-button>
</ion-nav-bar>--%>


<ion-nav-view></ion-nav-view>


<script id="templates/tabs.html" type="text/ng-template">
    <ion-tabs class="tabs-icon-top tabs-positive">

        <ion-tab title="Home" icon="ion-home" href="#/tab/home">
            <ion-nav-view name="home-tab"></ion-nav-view>
        </ion-tab>

        <ion-tab title="About" icon="ion-ios-information" href="#/tab/about">
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
            <p>
                <a class="button icon icon-right ion-chevron-right" href="#/tab/facts">Scientific Facts</a>
            </p>
            <p>
                <a class="button icon icon-right ion-chevron-right" href="#/tab/grid">表格数据</a>
            </p>
            <div class="card">
                <div class="item item-text-wrap">
                    基本卡片，包含了文本信息。
                </div>
            </div>




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
        <ion-content class="padding">
            <div class="row row-wrap">
                <div class="col col-25" ng-repeat="item in items">
                    <ul>
                        <li>
                            <img src="http://p4.music.126.net/LOEH8DU92vx2GJc0tX1xsA==/109951162971666277.jpg?param=200y200" ng-click="alertClick('{{item.text}}')">
                            <p>{{item.text}}</p>
                        </li>
                    </ul>
                </div>
            </div>
        </ion-content>
    </ion-view>
</script>
</body>
</html>
