var app=angular.module('ionicApp.controller', ['angularSoundManager','loWebManager','ionic']);

app.run(['angularPlayer','loWeb', function(angularPlayer,loWeb) {
	angularPlayer.setBootstrapTrack(
		loWeb.bootstrapTrack(
			function(){},
			function(){
				layer.msg("版权原因无法播放");
			})
	);
}]);



app.controller('GridTabCtrl',function($scope,$timeout, $ionicLoading,$state,$http,$ionicActionSheet,angularPlayer,$stateParams){
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
			"id": 0,
			"artist": "Lene Marlin",
			"title" : "A Place Nearby",
			"url" : "http://m10.music.126.net/20171013181431/1b59caf08b52a25ff98cc360ccb521af/ymusic/64b9/0c22/d174/ff11dba72f5b41720d04404c57d86c36.mp3",
			"img_url":"",
			"album":""
		}
		song.id=item.id;
		song.artist=item.artist;
		song.title=item.title;
		song.album=item.album;
		song.img_url=item.img_url;
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		queen.push(song);
		localStorage.setObject('queen',queen);
		angularPlayer.addTrackArray(queen);
		layer.msg("添加成功");
	}

	//添加并播放
	$scope.addAndPlay = function (item) {
		var song={
			"id": 0,
			"artist": "Lene Marlin",
			"title" : "A Place Nearby",
			"url" : "http://m10.music.126.net/20171013181431/1b59caf08b52a25ff98cc360ccb521af/ymusic/64b9/0c22/d174/ff11dba72f5b41720d04404c57d86c36.mp3",
			"img_url":"",
			"album":""
		}
		song.id=item.id;
		song.artist=item.artist;
		song.title=item.title;
		song.album=item.album;
		song.img_url=item.img_url;
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		queen.push(song);
		localStorage.setObject('queen',queen);
		angularPlayer.addTrack(song);
		angularPlayer.playTrack(song.id);
	}

	$scope.getSongList = function (list_id) {
		if(list_id==''){
			//layer.msg("非法刷新");
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
					$scope.addAndPlay(item);
					return true;
				}
				if(index==1){
                   $scope.addToQueen(item);
                   return true;
				}
				if(index==2){
					var deviceInformation = ionic.Platform.platform();
					if(deviceInformation=='ios'){
						layer.msg("暂不支持ios端下载");
						return true;
					}else{
						$scope.show();
						var url = getRootPath() + '/api/download?song_id=' + item.id.split("_").pop() + '&songName=' + item.title;
						window.location.href=url;
						$scope.hide();
						return true;
					}
				}
			}
		});
	}

	$scope.songList=[];
	$scope.title='';
	$scope.loadState=false;
	$scope.list_id=$stateParams.list_id;
	$scope.getSongList($scope.list_id);

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
			$timeout(function(){
				$scope.$broadcast('scroll.refreshComplete');
				// 停止广播上拉加载请求
				$scope.$broadcast('scroll.infiniteScrollComplete');
			},500);
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
});



//查询controller
app.controller('searchController',function($scope,$http,$state,$timeout,$ionicLoading,$ionicActionSheet,angularPlayer){
	$scope.keyword='';
	$scope.songList=[];
	$scope.offset=0;
	$scope.hasMore=false;
	$scope.show = function() {
		$ionicLoading.show({
			template: 'Loading...'
		});
	};
	$scope.hide = function(){
		$ionicLoading.hide();
	};

    Storage.prototype.setObject = function(key, value) {
        this.setItem(key, JSON.stringify(value));
    }

    Storage.prototype.getObject = function(key) {
        var value = this.getItem(key);
        return value && JSON.parse(value);
    }

    $scope.addToQueen = function (item) {
        var song={
            "id": 0,
            "artist": "Lene Marlin",
            "title" : "A Place Nearby",
            "url" : "http://m10.music.126.net/20171013181431/1b59caf08b52a25ff98cc360ccb521af/ymusic/64b9/0c22/d174/ff11dba72f5b41720d04404c57d86c36.mp3",
	        "img_url":"",
	        "album":""
        }
        song.id='netrack_'+item.id;
        song.artist=item.artists[0].name;
        song.title=item.name;
        song.album=item.album;
        song.img_url=item.album.blurPicUrl;
        var queen=localStorage.getObject('queen');
        if(queen==null)
            queen=[];
        queen.push(song);
        localStorage.setObject('queen',queen);
        angularPlayer.addTrackArray(queen);
        layer.msg("添加成功");
    }

	$scope.addAndPlay = function (item) {
		var song={
			"id": 0,
			"artist": "Lene Marlin",
			"title" : "A Place Nearby",
			"url" : "http://m10.music.126.net/20171013181431/1b59caf08b52a25ff98cc360ccb521af/ymusic/64b9/0c22/d174/ff11dba72f5b41720d04404c57d86c36.mp3",
			"img_url":"",
			"album":""
		}
		song.id='netrack_'+item.id;
		song.artist=item.artists[0].name;
		song.title=item.name;
		song.album=item.album;
		song.img_url=item.album.blurPicUrl;
		var queen=localStorage.getObject('queen');
		if(queen==null)
			queen=[];
		queen.push(song);
		localStorage.setObject('queen',queen);
		angularPlayer.addTrack(song);
		angularPlayer.playTrack(song.id);
	}

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
					$scope.addAndPlay(item);
					return true;
				}
				if(index==1){
					$scope.addToQueen(item);
					return true;
				}
				if(index==2){
                    var deviceInformation = ionic.Platform.platform();
                    if(deviceInformation=='ios'){
                        layer.msg("暂不支持ios端下载");
                        return true;
                    }else{
                        $scope.show();
                        var url = getRootPath() + '/api/download?song_id=' + item.id + '&songName=' + item.name;
                        window.location.href=url;
                        $scope.hide();
                        return true;
                    }
				}
			}
		});
	}
	//查询方法
	$scope.search=function(keyword){
		$scope.offset=0;
		if($scope.keyword==keyword)
			return;
		$scope.show();
		$http.post(
			getRootPath()+"/api/songSearch",
				'keyword='+keyword+'&offset=0'
			,{
				headers:{
					'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
				}
			}
		).success(function(data){
			$scope.songList=data.result.songs;
			$scope.hasMore=true;
			$scope.hide();
		}).error(
			function(data){
				$scope.hide();
			}
		);

	}


	//上拉加载更多
	//加载更多
	$scope.loadMore = function(keyword) {
		$scope.offset+=20;
		$http.post(
			getRootPath()+"/api/songSearch",
			'keyword='+keyword+'&offset='+$scope.offset
			,{
				headers:{
					'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
				}
			}
		).success(function(data){
			$scope.songList=$scope.songList.concat(data.result.songs);
			$scope.$broadcast('scroll.infiniteScrollComplete');
		}).error(
			function(data){
				$scope.$broadcast('scroll.infiniteScrollComplete');
			}
		);
	};





});


//播放器页面controller
app.controller('playController', ['$scope', 'angularPlayer','$ionicModal','$rootScope','$timeout','$filter', function($scope,angularPlayer,$ionicModal,$rootScope,$timeout,$filter) {
	Storage.prototype.setObject = function(key, value) {
		this.setItem(key, JSON.stringify(value));
	}

	Storage.prototype.getObject = function(key) {
		var value = this.getItem(key);
		return value && JSON.parse(value);
	}
	$scope.title='';
	$scope.songs=[];
	$scope.delete = {
		showDelete: false
	};
	$scope.isPlaying=false;//音乐是否在播放
	$scope.settings={//音乐播放模式
		playmode:0
	}


	//播放器初始化好后加载本地缓存
	$scope.$on('angularPlayer:ready', function(event, data) {
		$scope.repeat=  angularPlayer.getRepeatStatus();
		var localCurrentPlaying = localStorage.getObject('queen');
		$scope.songs=localCurrentPlaying;
		if (localCurrentPlaying == null) {
			return;
		}
		angularPlayer.addTrackArray(localCurrentPlaying);
	});

	//队列添加触发的事件
	$scope.$on('player:playlist',function(event,data){
		//layer.msg("添加队列事件触发");
		localStorage.setObject('queen', data);
		$scope.songs=data;
	});

	//正在播放时歌曲名称显示
	$scope.$on('music:isPlaying',function(event,data){
	    $scope.currentSong=angularPlayer.currentTrackData();
		if(data){
			var currentData = angularPlayer.currentTrackData();
			$scope.title=currentData.title;
            $scope.isPlaying=true;
		}else{
			$scope.title='';
            $scope.isPlaying=false;
		}
	});

	//删除队列中的歌曲
	$scope.onSongDelete = function(item){
		var index=$scope.songs.indexOf(item);
		angularPlayer.removeSong(item,index);
	}

	//清空歌单
	$scope.clearAll=function(){
		angularPlayer.stop();
		angularPlayer.setCurrentTrack(null);
		angularPlayer.clearPlaylist(function(data) {
			$log.debug('all clear!');
		});
	}


	//点击弹出歌曲详细进度页
    $ionicModal.fromTemplateUrl('templates/progress.html', {
        scope: $scope
    }).then(function(modal) {
        $scope.modal = modal;
    });

    //进度条
    $scope.myProgress = 0;

    $rootScope.$on('track:progress', function(event, data) {
        $scope.myProgress = data;
    });



    /*播放时间以及时长变化*/
    $scope.$on('currentTrack:position', function(event, data) {
        $timeout(function() {
            //$rootScope.currentPosition=$filter('humanTime')(data);
            $scope.currentPosition=$filter('humanTime')(data);
        });
    });
    $scope.$on('currentTrack:duration', function(event, data) {
        $timeout(function() {
            //$rootScope.currentDuration=$filter('humanTime')(data);
            $scope.currentDuration=$filter('humanTime')(data);
        });
    });

	/*播放模式变换*/
	function switchMode(mode){
		//playmode 0:loop 1:shuffle 2:repeat one
		switch(mode){
			case 0:
				if (angularPlayer.getShuffle()) {
					angularPlayer.toggleShuffle();
				}
				angularPlayer.setRepeatOneStatus(false);
				break;
			case 1:
				if (!angularPlayer.getShuffle()) {
					angularPlayer.toggleShuffle();
				}
				angularPlayer.setRepeatOneStatus(false);
				break;
			case 2:
				if (angularPlayer.getShuffle()) {
					angularPlayer.toggleShuffle();
				}
				angularPlayer.setRepeatOneStatus(true);
				break
		}
	}

	$scope.changePlaymode=function(){
		var modeCount=3;
		$scope.settings.playmode=($scope.settings.playmode+1)%modeCount;
		switchMode($scope.settings.playmode);
	}



}]);




//实时公交controller
app.controller('busController',['$scope','$timeout',function($scope,$timeout){
	//定义线路数组
	$scope.lines = [
		{key : "Google", value : "http://www.google.com"},
		{key : "Runoob", value : "http://www.runoob.com"},
		{key : "Taobao", value : "http://www.taobao.com"}
	];
	//定义方向数组
	$scope.directions=[
		{key:'351(丰台路口大观园西)',value:'123'},
		{key:'351(大观园西丰台路口)',value:'321'}
	];
	//定义站点数组
	$scope.stations=[
		{key:'樊家村',value:'2597'},
		{key:'北大地',value:'5424'}
	];

	$scope.change=function(item){
		alert(item.value);
		var taobao=item.key;
		if(taobao=='Runoob'){
			$scope.directions=[];
		}else{
			$scope.directions=[
				{key:'351(丰台路口大观园西)',value:'123'},
				{key:'351(大观园西丰台路口)',value:'321'}
			];
		}
	}
}])
