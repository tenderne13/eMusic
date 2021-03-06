var netPlayer = {
	params: {
		offset: 0,
		loading: '',
		playTracks: [],
		root: getRootPath()
	},




	showPlayList: function (order, offset) {
		var musicList = [];
		netPlayer.params.loading = layer.load(1, {shadeClose: true, shade: 0.4});
		$.ajax({
			url: netPlayer.params.root + '/api/playList',
			type: 'get',
			data: {
				offset: offset,
				order: order
			},
			async: false,
			success: function (data) {
				data = $.parseHTML(data);
				var list = $(data).find('.m-cvrlst li');
				list.each(function () {
					var default_playlist = {
						'cover_img_url': '',
						'title': '',
						'id': '',
						'source_url': ''
					};
					default_playlist.cover_img_url = $(this).find('img')[0].src;
					default_playlist.title = $(this).find('div a')[0].title;
					var url = $(this).find('div a')[0].href;
					var list_id = getParameterByName('id', url);
					default_playlist.id = 'neplaylist_' + list_id;
					default_playlist.source_url = 'http://music.163.com/#/playlist?id=' + list_id;
					musicList.push(default_playlist);
				});
				//增加偏移量
				netPlayer.params.offset += list.length;
			}
		});

		//直接内部渲染拼接
		netPlayer.listAppend(musicList);
		layer.close(netPlayer.params.loading);
		loading = false;
	},
	listRender: function (data) {
		if (data && data.length > 0) {
			var arr = [];
			data.forEach(function (item, index) {

				var strs='<a href="javaScript:;" onclick="netPlayer.orderDetail(\'' + item.id + '\');" class="weui-grid js_grid">\n' +
					'        <div class="weui-grid__icon">\n' +
					'            <img src="'+item.cover_img_url+'" alt="">\n' +
					'        </div>\n' +
					'        <p class="weui-grid__label">\n' +
									item.title +
					'        </p>\n' +
					'    </a>';
				arr.push(strs);
			});
			return arr.join('');
		} else {
			alert("数据异常");
		}
	},
	listAppend: function (data) {
		var strs = this.listRender(data);
		$("#music-container").append(strs);
	},
	orderDetail: function (id) {
		/*var page =layer.open({
				type: 2,
				content: netPlayer.params.root + '/route/wxOrderList?id=' + id,
				area: ['100%', '100%'],
				title: false,
				closeBtn: 0,
				shadeClose: false
			});
		layer.full(page);*/
		window.location.href=netPlayer.params.root + '/route/wxOrderList?id=' + id;
	},
	playList: function (id) {
		var listId = id.split("_").pop();
		var tracks = [];
		var info;
		$.ajax({
			url: netPlayer.params.root + '/api/orderList',
			type: 'get',
			data: {
				id: listId
			},
			async: false,
			success: function (data) {
				data = $.parseHTML(data);
				var dataObj = $(data);
				info = {
					'id': 'neplaylist_' + listId,
					'cover_img_url': dataObj.find('.u-cover img').attr('src'),
					'title': dataObj.find('.tit h2').text(),
					'source_url': 'http://music.163.com/#/playlist?id=' + listId,
				};

				var hrefs = dataObj.find('ul.f-hide li a');

				hrefs.each(function(index,item){
					var title = item.text;
					var url=$(item).attr('href');
					var id=url.split('=')[1];

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
					default_track.id = 'netrack_' + id;
					default_track.title = title;
					default_track.source_url = 'http://music.163.com/#/song?id=' + id;
					tracks.push(default_track);
				});
			}
		});
		var order = {
			"info": info,
			"tracks": tracks
		}
		this.playListRender(order);
	},
	//渲染器
	playListRender: function (order) {
		var info = order.info;
		var header='<div class="weui-panel__bd">\n' +
			'        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">\n' +
			'            <div class="weui-media-box__hd">\n' +
			'                <img class="weui-media-box__thumb" src="'+info.cover_img_url+'">\n' +
			'            </div>\n' +
			'            <div class="weui-media-box__bd">\n' +
			'                <h4 class="weui-media-box__title">'+info.title+'</h4>\n' +
			'            </div>\n' +
			'        </a>\n' +
			'    </div>';
		//$("#header").append(header);

		var tracks = order.tracks;

		var body = [];
		tracks.forEach(function (item, index) {
			var eoro = index % 2;
			var css;
			eoro == 0 ? css = "even" : css = "odd";

			var strs = '<li class="ng-scope ' + css + '">\n' +
				'                        <div class="col2">\n' +
				'                               <a class="ng-binding ng-scope ng-isolate-scope">' + item.title + '</a>' +
				'                        </div>\n' +
				'                        <div class="detail-tools">\n' +
				'                            <a title="添加到当前播放" class="detail-add-button ng-isolate-scope ng-hide" onclick=\'netPlayer.addToTracks("' + item.id + '","' + item.artist + '","' + item.img_url + '","' + item.title + '")\' ></a>\n' +
				'                            <a title="下载" class="detail-fav-button ng-hide" onclick=\'netPlayer.download("' + item.id + '","' + item.title + '")\'></a>\n' +
				'                            <a title="原始链接" href="' + item.source_url + '" class="source-button ng-isolate-scope ng-hide" target="_blank" ></a>\n' +
				'                        </div>\n' +
				'    </li>';
			var str ='<a class="weui-cell weui-cell_access" href="javascript:;" onclick="getOperation(\'' + item.id + '\',\'' + item.title + '\')">\n' +
				'        <div class="weui-cell__bd">\n' +
				'            <p>'+item.title+'</p>\n' +
				'        </div>\n' +
				'        <div class="weui-cell__ft">\n' +
				'        </div>\n' +
				'    </a>';
			body.push(str);
		});
		$("#orderList").append(body);
	},

	//定义加载更多方法
	loadingMore: function () {
		netPlayer.showPlayList('', netPlayer.params.offset);
	},

	//下载文件的方法
	download: function (id, songName) {
		netPlayer.params.loading = layer.load(1, {shadeClose: true, shade: 0.4, time: 3000});
		var song_id = id.slice('netrack_'.length);
		var url = netPlayer.params.root + '/api/download?song_id=' + song_id + '&songName=' + songName;
		try {
			window.location.href = url;
		} catch (e) {
			window.open("https://www.baidu.com/s?wd=" + e);
		}
	},

	//播放整个唱片
	playAlbum: function (id) {
		var listId = id.split("_").pop();
		$.ajax({
			url: netPlayer.params.root + '/api/orderList',
			type: 'get',
			data: {
				id: listId
			},
			async: false,
			success: function (data) {
				data = $.parseHTML(data);
				var dataObj = $(data);



				var hrefs = dataObj.find('ul.f-hide li a');

				hrefs.each(function(index,item){
					var title = item.text;
					var url=$(item).attr('href');
					var id=url.split('=')[1];

					var default_track = {
						'name': '',
						'url': '',
						'id': 'neartist_0',
						'cover': '',
						'author': '',
					};
					default_track.name = title;
					default_track.id = id;
					parent.play_Tracks.push(default_track);
				});



				//alert(parent.play_Tracks.length);
				parent.playCallback(parent.play_Tracks);
			}

		});
	},

	//将歌单添加到数组后的初始化播放器
	skPlayerInit: function (parentPlayer) {
		parentPlayer.destroy();
		parent.player = new skPlayer({
			autoplay: false,
			music: {
				type: 'file',
				source: play_Tracks
			}
		});
	},

	//单个歌曲添加到播放器
	addToTracks: function (id, artist, cover, title) {
		var default_track = {
			'id': '0',
			'name': '',
			'author': '',
			'src': 'jxq',
			'cover': '',
		};
		default_track.id = id.split("_").pop();
		default_track.name = title;
		default_track.author = artist;
		default_track.cover = cover;
		parent.play_Tracks.push(default_track);
		parent.playCallback(parent.play_Tracks);
	},

	//获得播放器需要的歌曲
	getPlayerAlbumSongs: function (id) {
		var tracks = [];
		$.ajax({
			url: netPlayer.params.root + '/api/orderList',
			type: 'get',
			data: {
				id: id
			},
			async: false,
			success: function (data) {
				data = $.parseHTML(data);
				var dataObj = $(data);
				var json_string = dataObj.find('textarea').val();
				var track_json_list = JSON.parse(json_string);
				$.each(track_json_list, function (index, track_json) {

					var default_track = {
						'name': '',
						'url': '',
						'song_id': 'neartist_0',
						'cover': '',
						'author': 'nealbum_0',
					};
					default_track.name = track_json.name;
					default_track.song_id = track_json.id;
					default_track.cover = track_json.album.picUrl;
					default_track.author = track_json.artists[0].name;

					tracks.push(default_track);
				});

			}
		});
		return tracks;
	}

}