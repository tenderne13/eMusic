var app=angular.module('ionicApp.controller', []);

app.controller('GridTabCtrl',function($scope,$timeout, $ionicLoading,$state,$http,$ionicActionSheet){
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
			layer.msg("非法刷新");
			//$state.go('tabs.home');
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
				$scope.title=info.title;
				$scope.loadState=true;
				$scope.hide();
			});
		}else{
			layer.msg("非法刷新");
		}

	}

	//点击歌曲显示actionsheet
	$scope.getSongOperation=function(item){
		$ionicActionSheet.show({
			buttons: [
				{ text: '播放' },
				{ text: '添加到播放队列' },
				{ text: '下载' }
			],
			titleText: item.title,
			cancelText: '取消',
			cancel: function() {
				// add cancel code..
			},
			buttonClicked: function(index) {
				if(index==0){
					layer.msg("建设中。。。");
					return true;
				}
				if(index==1){
					layer.msg("建设中。。。");
					return true;
				}
				if(index==2){
					$scope.show();
					var song_id = item.id.slice('netrack_'.length);
					var url = getRootPath() + '/api/download?song_id=' + song_id + '&songName=' + item.title;
					window.location.href=url;
					$scope.hide();
					return true;
				}
			}
		});
	}

	$scope.songList=[];
	$scope.title='';
	$scope.loadState=false;
	$scope.list_id=$state.params.list_id;
	$scope.getSongList($scope.list_id);
	/*$scope.$on('$stateChangeSuccess', function() {
        $scope.getSongList($scope.list_id);
	});*/

});





//首页歌单加载页面
app.controller('HomeTabCtrl', function($scope,$http,$state,$timeout) {
	$scope.offset=0;
	$scope.musicList=[];
	$scope.loadMoreState=true;

	//获取歌单列表
	$scope.getPlayList=function(order,offset){
		$http.get(getRootPath() + '/api/albumList?offset='+offset+'&order='+order).success(function(data){
			var albumList=data.albumList;
			$scope.offset+=albumList.length;
			$scope.musicList=$scope.musicList.concat(albumList);
			$scope.loadMoreState=true;
		});

		$timeout(function(){
			$scope.$broadcast('scroll.refreshComplete');
			// 停止广播上拉加载请求
			$scope.$broadcast('scroll.infiniteScrollComplete');
		},1500);



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
	//$scope.loadMore();
	/*$scope.$on('$stateChangeSuccess', function() {
		$scope.loadMore();
	});*/
});



//查询controller
app.controller('searchController',function($scope,$http,$state,$timeout,$ionicLoading,$ionicActionSheet){
	$scope.keyword='';
	$scope.songList=[];
	$scope.show = function() {
		$ionicLoading.show({
			template: 'Loading...'
		});
	};
	$scope.hide = function(){
		$ionicLoading.hide();
	};
	//点击歌曲显示actionsheet
	$scope.getSongOperation=function(item){
		$ionicActionSheet.show({
			buttons: [
				{ text: '播放' },
				{ text: '添加到播放队列' },
				{ text: '下载' }
			],
			titleText: item.name,
			cancelText: '取消',
			cancel: function() {
				// add cancel code..
			},
			buttonClicked: function(index) {
				if(index==0){
					layer.msg("建设中。。。");
					return true;
				}
				if(index==1){
					layer.msg("建设中。。。");
					return true;
				}
				if(index==2){
					$scope.show();
					var url = getRootPath() + '/api/download?song_id=' + item.id + '&songName=' + item.name;
					window.location.href=url;
					$scope.hide();
					return true;
				}
			}
		});
	}
	//查询方法
	$scope.search=function(keyword){

		if($scope.keyword==keyword)
			return;
		$scope.show();
		$http.post(
			getRootPath()+"/api/songSearch",
				'keyword='+keyword
			,{
				headers:{
					'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
				}
			}
		).success(function(data){
			$scope.songList=data.result.songs;
			$scope.hide();
		}).error(
			function(data){
				$scope.hide();
			}
		);

	}

});