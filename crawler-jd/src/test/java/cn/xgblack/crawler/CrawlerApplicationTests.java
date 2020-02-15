package cn.xgblack.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class CrawlerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void readHtml() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        Elements spuEles = doc.select("#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            System.out.println("spu:  " + spuEle.attr("data-spu"));
            Elements skuEles = spuEle.select("ul.ps-main > li.ps-item img");
            //System.out.println(skuEles.toString());
            for (Element skuEle : skuEles) {
                System.out.println("sku:  " + skuEle.attr("data-sku") + "--- img:   " + skuEle.attr("data-lazy-img"));
            }
            System.out.println("**********************************************");
        }
    }

}
