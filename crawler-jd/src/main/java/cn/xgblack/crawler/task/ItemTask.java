package cn.xgblack.crawler.task;

import cn.xgblack.crawler.entity.JdItem;
import cn.xgblack.crawler.service.JdItemService;
import cn.xgblack.crawler.utils.HttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author xg BLACK
 * @date 2020/2/15 10:34
 * description: 定时任务
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private JdItemService jdItemService;

    @Autowired
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //当下载任务完成后，间隔多长时间进行下一次任务
    @Scheduled(fixedDelay = 300 * 1000)
    public void itemTask(){
        //声明需要解析的初始地址
        String url = "http://search.jd.com/Search?keyword=手机&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=手机&s=340&click=0&page=";
        //下载页 page=1,3,5,7,9
        for (int i = 1; i < 12; i = i + 2) {
            System.out.println("开始爬取第" + (i / 2 + 1) + "页... >" + url + i);
            String html = httpUtils.doGetHtml(url + i);
            //解析页面，获取商品数据并存储
            this.parse(html);
        }

        System.out.println(">>> 收集数据抓取完成！");
    }

    //解析页面，获取商品数据并存储
    private void parse(String html) {
        //解析HTML获取dom对象
        Document doc = Jsoup.parse(html);

        //获取spu
        Elements spuEles = doc.select("#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            //获取spu
            long spu = Long.parseLong(spuEle.attr("data-spu"));

            //获取sku
            Elements skuEles = spuEle.select("ul.ps-main > li.ps-item img");
            for (Element skuEle : skuEles) {
                Long sku = Long.parseLong(skuEle.attr("data-sku"));
                JdItem item = new JdItem();
                //设置商品spu
                item.setSpu(spu);
                item.setSku(sku);
                List<JdItem> items = this.jdItemService.queryAll(item);
                if (items.size() > 0) {
                    //根据sku查询商品数据，如果存在，则进行下一个循环，该商品不保存，因为已存在
                    continue;
                }

                //获取商品详情的url
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(itemUrl);

                //商品图片
                String picUrlStr = skuEle.attr("data-lazy-img");

                if (picUrlStr == null || "".equals(picUrlStr)) {
                    //部分商品图片规则不一样
                    picUrlStr = skuEle.attr("data-lazy-img-slave");
                }
                String picUrl = "https:" + picUrlStr;
                picUrl = picUrl.replace("/n9/", "/n1/");

                item.setPic(httpUtils.doGetImage(picUrl));


                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                try {
                    double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                    item.setPrice(price);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                //标题
                String htmlInfo = this.httpUtils.doGetHtml(item.getUrl());
                Document itemDoc = Jsoup.parse(htmlInfo);
                String title = itemDoc.select("div.sku-name").first().text();
                item.setTitle(title);

                //创建时间
                item.setCreated(new Date());

                System.out.println("爬取 >> " + item);

                this.jdItemService.insert(item);
            }
        }
    }
}
