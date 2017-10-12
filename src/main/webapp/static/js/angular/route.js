var app=angular.module('ionicApp', ['ionic','ionicApp.controller']);

//config 路由配置
app.config(function($stateProvider, $urlRouterProvider) {

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
			params:{
				list_id:null
			},
			views: {
				'home-tab': {
					templateUrl: "templates/grid.html",
					controller: 'GridTabCtrl'
				}
			}
		})
		.state('tabs.music', {
			url: "/music",
			views: {
				'music-tab': {
					templateUrl: "templates/music.html"
				}
			}
		})
		.state('tabs.search', {
			url: "/search",
			views: {
				'search-tab': {
					templateUrl: "templates/search.html",
					controller:'searchController'
				}
			}
		});
	$urlRouterProvider.otherwise("/tab/home");

});





