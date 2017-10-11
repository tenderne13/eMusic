var app=angular.module('ionicApp', ['ionic']);
app.factory('musicService', function() {
	var getPlayList = function (order, offset) {
		var musicList = [];
		/*$.ajax({
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
				offset += list.length;
			}
		});*/

		return musicList;
	}

	return getPlayList;
});