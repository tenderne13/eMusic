var app=angular.module('ionicApp', ['ionic','ionicApp.controller']);

//config 路由配置
app.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {
    $ionicConfigProvider.tabs.position('bottom');
    $ionicConfigProvider.tabs.style('standard');
    $ionicConfigProvider.navBar.alignTitle('center');
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
			//cache:false,
			views: {
				'music-tab': {
					templateUrl: "templates/music.html",
					controller:'playController'
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
		})
		.state('tabs.bus',{
			url: "/bus",
			views:{
				'bus-tab':{
					templateUrl:"templates/bus.html",
					controller:'busController'
				}
			}
		});
	$urlRouterProvider.otherwise("/tab/home");

});





