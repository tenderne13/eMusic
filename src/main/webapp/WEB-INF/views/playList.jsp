<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE>
<html>
<head>
    <title>网易云音乐</title>
    <script src="${ctx}/static/js/jquery-1.12.2.js" ></script>
    <script src="${ctx}/static/js/aes.js" ></script>
    <script src="${ctx}/static/js/bigint.js" ></script>
    <script src="${ctx}/static/js/lowebutil.js" ></script>
    <script src="${ctx}/static/js/myMusic.js" ></script>
    <link href="${ctx}/static/css/core.css" rel="stylesheet"  />
    <link href="${ctx}/static/css/pt_frame.css" rel="stylesheet"  />

</head>
<script type="text/javascript">
    $(function(){
    	netPlayer.showPlayList('hot',35);
    });

</script>
<body>

    <div class="g-bd" id="m-disc-pl-c">
        <div class="g-wrap p-pl f-pr">
            <div class="u-title f-cb">
                <h3><span class="f-ff2 d-flag">全部</span><%--<a href="javascript:;" class="u-btn2 u-btn2-1 menu d-flag" id="cateToggleLink"><i>选择分类<em class="u-icn u-icn-38"></em></i></a>--%></h3>
            </div>
            <ul class="m-cvrlst f-cb" id="m-pl-container">
                <li>
                    <div class="u-cover u-cover-1">
                        <img class="j-flag" src="http://p1.music.126.net/NjcJcONQbWgGxWdYWRTWVQ==/109951163023946099.jpg?param=140y140">
                        <a title="大爷您歇着，我们小鲜肉能行！" href="/playlist?id=915572360" class="msk"></a>

                    </div>
                    <p class="dec">
                        <a title="大爷您歇着，我们小鲜肉能行！" href="/playlist?id=915572360" class="tit f-thide s-fc0">大爷您歇着，我们小鲜肉能行！</a>
                    </p>
                    <p><span class="s-fc4">by</span> <a title="乐清Jack" href="/user/home?id=545735040" class="nm nm-icn f-thide s-fc3">乐清Jack</a> </p>
                </li>
                <li>
                    <div class="u-cover u-cover-1">
                        <img class="j-flag" src="http://p1.music.126.net/GrgF7XieBUkVTRIubfNN0w==/3272146606957858.jpg?param=140y140">
                        <a title="爆炸Trap｜放学的路上你狂嗨然后无辜被打" href="/playlist?id=121820933" class="msk"></a>
                        <div class="bottom">
                            <a class="icon-play f-fr" title="播放" href="javascript:;" data-res-type="13" data-res-id="121820933" data-res-action="play"></a>
                            <span class="icon-headset"></span>
                            <span class="nb">22993</span>
                        </div>
                    </div>
                    <p class="dec">
                        <a title="爆炸Trap｜放学的路上你狂嗨然后无辜被打" href="/playlist?id=121820933" class="tit f-thide s-fc0">爆炸Trap｜放学的路上你狂嗨然后无辜被打</a>
                    </p>
                    <p><span class="s-fc4">by</span> <a title="丿曰月" href="/user/home?id=2875681" class="nm nm-icn f-thide s-fc3">丿曰月</a> <sup class="u-icn u-icn-84 "></sup>
                    </p>
                </li>
                <li>
                    <div class="u-cover u-cover-1">
                        <img class="j-flag" src="http://p1.music.126.net/uVdH7yeKslVOmD_Ot5Eeug==/19193074974757493.jpg?param=140y140">
                        <a title="【粤语民谣〗- 品味清新恬淡的港式文艺" href="/playlist?id=881819932" class="msk"></a>
                        <div class="bottom">
                            <a class="icon-play f-fr" title="播放" href="javascript:;" data-res-type="13" data-res-id="881819932" data-res-action="play"></a>
                            <span class="icon-headset"></span>
                            <span class="nb">12573</span>
                        </div>
                    </div>
                    <p class="dec">
                        <a title="【粤语民谣〗- 品味清新恬淡的港式文艺" href="/playlist?id=881819932" class="tit f-thide s-fc0">【粤语民谣〗- 品味清新恬淡的港式文艺</a>
                    </p>
                    <p><span class="s-fc4">by</span> <a title="潆流" href="/user/home?id=539619897" class="nm nm-icn f-thide s-fc3">潆流</a> </p>
                </li>
                <li>
                    <div class="u-cover u-cover-1">
                        <img class="j-flag" src="http://p1.music.126.net/F5kaofTF77UDKQcQcyFoQw==/109951163024424865.jpg?param=140y140">
                        <a title="【舒缓集】柔若清风，静谧曲调" href="/playlist?id=491422649" class="msk"></a>
                        <div class="bottom">
                            <a class="icon-play f-fr" title="播放" href="javascript:;" data-res-type="13" data-res-id="491422649" data-res-action="play"></a>
                            <span class="icon-headset"></span>
                            <span class="nb">8768</span>
                        </div>
                    </div>
                    <p class="dec">
                        <a title="【舒缓集】柔若清风，静谧曲调" href="/playlist?id=491422649" class="tit f-thide s-fc0">【舒缓集】柔若清风，静谧曲调</a>
                    </p>
                    <p><span class="s-fc4">by</span> <a title="Fancy-FIRE" href="/user/home?id=35284296" class="nm nm-icn f-thide s-fc3">Fancy-FIRE</a> <sup class="u-icn u-icn-84 "></sup>
                    </p>
                </li>
                <li>
                    <div class="u-cover u-cover-1">
                        <img class="j-flag" src="http://p1.music.126.net/rwfXhKMJtOeJ_vnemkAm4g==/109951162901884794.jpg?param=140y140">
                        <a title="『动漫原声』如何优雅的参加晚宴" href="/playlist?id=693785920" class="msk"></a>
                        <div class="bottom">
                            <a class="icon-play f-fr" title="播放" href="javascript:;" data-res-type="13" data-res-id="693785920" data-res-action="play"></a>
                            <span class="icon-headset"></span>
                            <span class="nb">3749</span>
                        </div>
                    </div>
                    <p class="dec">
                        <a title="『动漫原声』如何优雅的参加晚宴" href="/playlist?id=693785920" class="tit f-thide s-fc0">『动漫原声』如何优雅的参加晚宴</a>
                    </p>
                    <p><span class="s-fc4">by</span> <a title="三角仔" href="/user/home?id=73980758" class="nm nm-icn f-thide s-fc3">三角仔</a> <sup class="u-icn u-icn-84 "></sup>
                    </p>
                </li>
            </ul>
            <div id="m-pl-pager">

            </div>
        </div>
    </div>
</body>
</html>
