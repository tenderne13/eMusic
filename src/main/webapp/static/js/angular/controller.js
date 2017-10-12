var app=angular.module('ionicApp.controller', []);

app.controller('GridTabCtrl',function($scope,$timeout, $ionicLoading,$state,$http,$ionicActionSheet){
	Storage.prototype.setObject = function(key, value) {
		this.setItem(key, JSON.stringify(value));
	}

	Storage.prototype.getObject = function(key) {
		var value = this.getItem(key);
		return value && JSON.parse(value);
	}

	$scope.show = function() {
		$ionicLoading.show({
			template: 'Loading...'
		});
	};
	$scope.hide = function(){
		$ionicLoading.hide();
	};
	$scope.addToQueen = function (item) {
		var song={
			"song_id": 0,
			"artist": "Lene Marlin",
			"song" : "A Place Nearby",
			"album" : "《Playing My Game》",
			"songUrl" : "",
			"avatar" : "http://p4.music.126.net/LOEH8DU92vx2GJc0tX1xsA==/109951162971666277.jpg?param=200y200"
		}
		song.song_id=item.id;
		song.artist=item.artist;
		song.song=item.title;
		song.album=item.album;
		song.avatar=item.img_url;
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		queen.push(song);
		localStorage.setObject('queen',queen);

		//console.log(localStorage.getObject('queen'));
		layer.msg("添加到队列中");


	}

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
					$scope.addToQueen(item);
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


//播放器页面controller
app.controller('playController', ['$scope', 'DataList', 'DataBinding', 'Audio', 'Player', '$timeout', function($scope, DataList, DataBinding, Audio, Player, $timeout) {
	Storage.prototype.setObject = function(key, value) {
		this.setItem(key, JSON.stringify(value));
	}

	Storage.prototype.getObject = function(key) {
		var value = this.getItem(key);
		return value && JSON.parse(value);
	}

	//DataBinding.dataBindFunc(0);//默认绑定的数据

	//本应用重点部分：控制播放器
	$scope.player = Player;
	$scope.audio = Audio;
	$scope.player.active = 0;
	$scope.data=[];

	//$scope.player.controllPlay($scope.player.active);
	//$scope.player.playerSrc($scope.player.active);
	//$scope.player.controllPlay($scope.player.active);
	$scope.isSelected = function() {
		$scope.player.active = this.$index; //给当前的li添加.icon-music
		//DataBinding.dataBindFunc($scope.player.active);//绑定数据
		$scope.player.controllPlay($scope.player.active); //播放当前的音频
	};

	$scope.loadCache=function(){
		/*layer.msg("刷新队列");
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		$scope.data=queen;*/
		$scope.player=Player;
	}

	$scope.$on('$stateChangeSuccess', function() {
		$scope.loadCache();
	});


}]);



app.factory('DataList', function() {
	var data={};
	return data;
});

//Binding Data Service
app.factory('DataBinding', ['$rootScope', 'DataList', function($rootScope, DataList) {
	Storage.prototype.setObject = function(key, value) {
		this.setItem(key, JSON.stringify(value));
	}

	Storage.prototype.getObject = function(key) {
		var value = this.getItem(key);
		return value && JSON.parse(value);
	}
	//var queen=localStorage.getItem('');
	var queen=localStorage.getObject('queen');
	if(queen==null)
		queen=[];
	//$rootScope.datas = DataList;
	$rootScope.datas = queen;

	var dataObj = {
		dataBindFunc: function(index) {
			$rootScope.avatar = $rootScope.datas[index].avatar;
			$rootScope.artist = $rootScope.datas[index].artist;
			$rootScope.song = $rootScope.datas[index].song;
			$rootScope.album = $rootScope.datas[index].album;
		}
	};

	return dataObj;
}]);

//Audio Service
app.factory('Audio', ['$document', function($document) {
	var audio = $document[0].createElement('audio');
	return audio;
}]);

//Player Service
app.factory('Player', ['$rootScope', '$interval' ,'Audio', 'DataList', 'DataBinding','$http', function($rootScope, $interval, Audio, DataList, DataBinding,$http) {
	//$rootScope.data = DataList;
	Storage.prototype.setObject = function(key, value) {
		this.setItem(key, JSON.stringify(value));
	}

	Storage.prototype.getObject = function(key) {
		var value = this.getItem(key);
		return value && JSON.parse(value);
	}
	var queen=localStorage.getItem('queen');
	if(queen==null)
		queen=[];

	//$rootScope.datas = DataList;
	$rootScope.data = queen;
	
	$rootScope.loadCache=function () {
		layer.msg("刷新队列");
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		$rootScope.data=queen;
		console.log($rootScope.data);
	}

	var player = {
		musicLen: '7',
		controllPlay: function(index) {
			$rootScope.loadCache();
			player.playerSrc(index);
			//player.play();//播放
			player.isPlay = true;//让图片转动
			//DataBinding.dataBindFunc(index);//显示当前播放歌曲的信息
			player.playing = true;//显示暂停按钮
		},
		playerSrc: function(index) { //Audio的url
			//var url = $rootScope.data[index].songUrl;
			var song_id=$rootScope.data[index].song_id;
			song_id=song_id.slice('netrack_'.length);
			$http.get(getRootPath()+'/api/getSongUrl?song_id='+song_id).success(function(data){
				Audio.src = data.url;
				player.play();
			});

			/*$.ajax({
				url: getRootPath() + '/api/getSongUrl',
				type: 'get',
				data: {
					song_id: song_id
				},
				async: false,
				success: function (data) {
					data = JSON.parse(data);
					$rootScope.$apply(function(){
						Audio.src=data.url;
					});
				}
			});*/

		},
		play: function(index) { //播放
			if(player.playing) {
				player.stop();
			}

			Audio.play(); //h5 audio api
			player.isPlay = true; //图片转动
			player.playing = true; //显示暂停按钮
		},
		stop: function() { //暂停
			if(player.playing) {
				Audio.pause();
			}

			player.isPlay = false; //图片停止转动
			player.playing = false;//显示播放按钮
		},
		prev: function(index) { //上一首歌
			console.log('prev:' + player.active);

			if(player.active == 0) { //如果是第一首音乐
				player.active = player.musicLen - 1;  //播放最后一首
			} else {
				player.active -= 1; //否则递减
			}

			player.controllPlay(player.active);
		},
		next: function(index) { //下一首歌
			console.log('next:' + player.active);

			if(player.active == (player.musicLen - 1)) {
				player.active = 0;
			} else {
				player.active += 1;
			}

			player.controllPlay(player.active); //播放显示的数据
		}
	};

	return player;
}]);
