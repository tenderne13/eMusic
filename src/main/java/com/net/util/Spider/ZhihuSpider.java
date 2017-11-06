package com.net.util.Spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class ZhihuSpider implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(300);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> imgs = html.$("img","data-original").all();
        for(String img : imgs){
            System.out.println(img);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhihuSpider()).addUrl("https://www.zhihu.com/question/67352049/answer/255421027").thread(3).run();
    }
}
