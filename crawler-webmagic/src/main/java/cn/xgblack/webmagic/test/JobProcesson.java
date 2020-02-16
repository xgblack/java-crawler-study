package cn.xgblack.webmagic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author xg BLACK
 * @date 2020/2/16 14:45
 * description:
 */
public class JobProcesson implements PageProcessor {
    //负责解析页面

    public void process(Page page) {
        //解析page，并且把解析结果放到ResultItems中
        page.putField("as", page.getHtml().css("div.hotnews li a").all());

        //Xpath
        page.putField("a2", page.getHtml().xpath("//div[@id=headline-tabs]/ul/li/a"));

        //正则表达式
        page.putField("as3", page.getHtml().css("div.mod-tab-content a").regex(".*http.*").all());

        //处理结果的api
        page.putField("as4", page.getHtml().css("div.mod-tab-content a").regex(".*http.*"));
        page.putField("as5", page.getHtml().css("div.mod-tab-content a").regex(".*http.*").get());
        page.putField("as6", page.getHtml().css("div.mod-tab-content a").regex(".*http.*").toString());

        //获取链接
        //page.addTargetRequests(page.getHtml().css("div.mod-tab-content a").links().regex(".+news\\.cctv\\.com.*").all());
        //page.putField("【h1】",page.getHtml().$("#title_area > h1").all());
        page.addTargetRequests(page.getHtml().css("div.mod-tab-content a").links().all());
        page.putField("【h1】", page.getHtml().$("h1").all());
    }

    private Site site = Site.me()
            .setCharset("utf8")//设置编码
            .setTimeOut(1000)//设置超时时间  ms
            .setRetrySleepTime(3000)//设置重试的时间间隔  ms
            .setSleepTime(3)//设置重试次数
            ;

    public Site getSite() {
        return site;
    }

    //主函数，执行爬虫
    public static void main(String[] args) {
        Spider.create(new JobProcesson())
                .addUrl("http://news.baidu.com/")//设置要爬取数据的页面
                .addPipeline(new FilePipeline("D:/下载/test"))//保存方式
                .thread(5)//设置线程数
                .run();//执行爬虫
    }
}
