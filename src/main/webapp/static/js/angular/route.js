var app=angular.module('ionicApp', ['ionic']);

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

});



app.controller('GridTabCtrl',function($scope,$timeout, $ionicLoading,$state,$http){
	$scope.show = function() {
		$ionicLoading.show({
			template: 'Loading...'
		});
	};
	$scope.hide = function(){
		$ionicLoading.hide();
	};

	$scope.getSongList = function (list_id) {
		if(list_id==null){
			$state.go('tabs.home');
		}else if(!$scope.loadState){
			$scope.show();
			var listId = list_id.split("_").pop();
			var tracks = [];
			var info;
			$http.get(getRootPath() + '/api/orderList?id='+listId).success(function(data){
				info = {
					'id': 'neplaylist_' + listId,
					'cover_img_url': data.result.coverImgUrl,
					'title': data.result.name,
					'source_url': 'http://music.163.com/#/playlist?id=' + listId,
				};

				$.each(data.result.tracks, function (index, track_json) {
					var default_track = {
						'id': '0',
						'title': '',
						'artist': '',
						'artist_id': 'neartist_0',
						'album': '',
						'album_id': 'nealbum_0',
						'source': 'netease',
						'source_url': 'http://www.xiami.com/song/0',
						'img_url': '',
						'url': ''
					};
					default_track.id = 'netrack_' + track_json.id;
					default_track.title = track_json.name;
					default_track.artist = track_json.artists[0].name;
					default_track.artist_id = 'neartist_' + track_json.artists[0].id;
					default_track.album = track_json.album.name;
					default_track.album_id = 'nealbum_' + track_json.album.id;
					default_track.source_url = 'http://music.163.com/#/song?id=' + track_json.id;
					default_track.img_url = track_json.album.picUrl;
					default_track.url = default_track.id;

					tracks.push(default_track);
				});
				$scope.songList=tracks;
				$scope.loadState=true;
				$scope.hide();
			});
		}else{
			$state.go('tabs.home');
		}

	}
	$scope.songList=[];
	$scope.loadState=false;
	$scope.list_id=$state.params.list_id;
	//var list_id = $state.params.list_id;
	$scope.getSongList($scope.list_id);
	/*$scope.$on('$stateChangeSuccess', function() {

	});*/

});





//首页歌单加载页面
app.controller('HomeTabCtrl', function($scope,$http,$state) {
	$scope.offset=0;
	$scope.musicList=[];

	//获取歌单列表
	$scope.getPlayList=function(order,offset){
		$http.get(getRootPath() + '/api/albumList?offset='+offset+'&order='+order).success(function(data){
			var albumList=data.albumList;
			$scope.offset+=albumList.length;
			$scope.musicList=$scope.musicList.concat(albumList);
			$scope.$broadcast('scroll.infiniteScrollComplete');
			$scope.$broadcast('scroll.refreshComplete');
		});
	}
	//下拉刷新
	$scope.doRefresh = function() {
		$scope.musicList=[];
		$scope.offset=0;
		$scope.getPlayList('hot',$scope.offset);
	};
	//加载更多
	$scope.loadMore = function() {
		$scope.getPlayList('hot',$scope.offset);
	};
	//获取一个歌单的详细信息
	$scope.getAlbumDetail=function(id){
		$state.go("tabs.grid", {list_id:id});
	}


	$scope.$on('$stateChangeSuccess', function() {
		$scope.loadMore();
	});

});

