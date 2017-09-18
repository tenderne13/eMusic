package com.net.util;

import java.util.Properties;

public class Constant {
    public static String URL="http://music.163.com/discover/playlist/";
    public static String ORDER_URL="http://music.163.com/playlist?id=";//http://163.opdays.com/playlist?id=910636600
    public static String TARGERT_SONG_URL="http://music.163.com/weapi/song/enhance/player/url?csrf_token=";
    public static String MUSIC_PATH;
    static {
        Properties properties = new Properties();
        try {
            properties.load(Constant.class.getClassLoader().getResourceAsStream("path.properties"));
            MUSIC_PATH=properties.getProperty("music.path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
