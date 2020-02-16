package cn.xgblack.webmagic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
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
        page.putField("div",page.getHtml().css("div.dt a").all());
    }

    private Site site = Site.me();

    public Site getSite() {
        return site;
    }

    //主函数，执行爬虫
    public static void main(String[] args) {
        Spider.create(new JobProcesson())
                .addUrl("https://kuaibao.jd.com/")//设置要爬取数据的页面
                .run();//执行爬虫
    }
}
