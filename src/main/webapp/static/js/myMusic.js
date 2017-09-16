var netPlayer=(function(){
	'use strict';
	//定义offset全局变量
	var offset=0;


	//定义一个获得歌单的方法
	var root=getRootPath();
	var showPlayList=function (order,offset) {
		var musicList=[];
		$.ajax({
			url:root+'/api/playList',
			type:'get',
			data:{
				offset:offset,
				order:order
			},
			async:false,
			success:function(data){
				data=$.parseHTML(data);
				var list = $(data).find('.m-cvrlst li');
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
				//增加偏移量
				netPlayer.offset+=list.length;
		}
		});

		//直接内部渲染拼接
		this.listAppend(musicList);


        /*return {
            result:musicList
        }*/
	}


	//歌单拼装渲染器
    function listRender(data){
        if(data && data.length>0){
            var arr=[];
            data.forEach(function(item,index){
                var str=             "<li onclick=\"netPlayer.orderDetail('"+item.id+"');\">" +
                    "                    <div class=\"u-cover u-cover-1\" data-id='"+item.id+"'>" +
                    "                        <img class=\"j-flag\" src=\""+item.cover_img_url+"\">" +
                    "                        <a title=\""+item.title+"\" href=\"javaScript:;\" class=\"msk\"></a>" +
                    "                    </div>" +
                    "                    <p class=\"dec\">" +
                    "                        <a title=\""+item.title+"\" href=\"javaScript:;\" class=\"tit f-thide s-fc0\">"+item.title+"</a>" +
                    "                    </p>" +
                    "                </li>";

                arr.push(str);
            });
            return arr.join('');
        }else{
            alert("数据异常");
        }
    }

    //onclick事件
	function orderDetail(id){
    	alert(id);
	}


    //拼接类
    var listAppend=function(data){
        var strs=listRender(data);
        $("#music-container").append(strs);
        var mList=$("#music-container").find('li');
        /*mList.each(function(){
                var id = $(this).find('div').attr("data-id");
                $(this).click(function(){
                    alert(id);
                });
        });*/
    }



    //定义加载更多方法
	var loadingMore=function(){
    	netPlayer.showPlayList('',netPlayer.offset);
	}




	return {
		showPlayList:showPlayList,
		loadingMore:loadingMore,
		listAppend:listAppend,
		offset:offset,
		orderDetail:orderDetail
	}


})();