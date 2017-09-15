var netPlayer=(function(){
	'use strict';
	//定义一个获得歌单的方法
	var showPlayList=function (url) {
		var order='hot';
		var offset=getParameterByName('offset',url);
		var target_url;
		if(offset!=null){
			target_url = 'http://music.163.com/discover/playlist/?order=' + order + '&limit=35&offset=' + offset;
		}else{
			target_url='http://music.163.com/discover/playlist/?order=' + order;
		}

		var musicList=[];
		$.ajax({
			url:'http://localhost:8080/netMusic/api/playList',
			type:'get',
			async:false,
			success:function(data){
				data=$.parseHTML(data);
				var list = $(data).find('.m-cvrlst li');
				console.log(list);
				alert(list.length);
				list.each(function(){
					var default_playlist = {
						'cover_img_url' : '',
						'title': '',
						'id': '',
						'source_url': ''
					};
					default_playlist.cover_img_url = $(this).find('img')[0].src;
					default_playlist.title = $(this).find('div a')[0].title;
					var url = $(this).find('div a')[0].href;
					var list_id = getParameterByName('id',url);
					default_playlist.id = 'neplaylist_' + list_id;
					default_playlist.source_url = 'http://music.163.com/#/playlist?id=' + list_id;
					musicList.push(default_playlist);
				});
		}
		});
		console.log(musicList);
		alert(musicList.length);

	}

	return {
		showPlayList:showPlayList
	}


})();