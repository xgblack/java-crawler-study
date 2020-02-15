package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * @author xg BLACK
 * @date 2020/2/7 11:03
 * description:
 */
public class JsoupFirstTest {

    @Test
    public void testUrl() throws IOException {
        //解析URL
        //第一个参数，访问的URL。第二个参数访问的时候的超时时间
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);

        //使用标签选择器，获取title标签中的内容
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testString() throws IOException {
        //实用工具类读取文件
        String content = FileUtils.readFileToString(new File("src/main/resources/test.html"), "utf8");
        //System.out.println(content);
        //解析字符串
        Document doc = Jsoup.parse(content);

        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testFile() throws IOException {
        //解析文件
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testDom() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        //id
        //Element element = doc.getElementById("webimclosebutton");


        //根据标签获取元素
        //Element element = doc.getElementsByTag("span").first();


        //根据class获取
        //Element element = doc.getElementsByClass("city_in").first();


        //根据属性
        //Element element = doc.getElementsByAttribute("abc").first();

        Element element = doc.getElementsByAttributeValue("href", "http://sy.itcast.cn").first();
        System.out.println("获取到的元素内容是:" + element.text());
    }

    @Test
    public void testData() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        Element element = doc.getElementById("test");
        //获取id
        String id = element.id();
        System.out.println(id);

        String className = element.className();
        System.out.println(className);

        Set<String> classNames = element.classNames();
        System.out.println(classNames);

        //获取属性的值
        String idValue = element.attr("id");
        String classValue = element.attr("class");
        System.out.println(idValue);
        System.out.println(classValue);

        //获取所有的属性
        Attributes attributes = element.attributes();
        System.out.println(attributes.toString());

        String text = element.text();
        System.out.println(text);
    }

    @Test
    public void testSelector() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        //通过标签查找元素
        Elements elements = doc.select("span");
        for (Element element : elements) {
            //System.out.println(element);
        }

        //System.out.println("********************");

        //通过id查找
        Element idElement = doc.select("#test").first();
        //System.out.println(idElement);

        //通过属性查找
        //Element element = doc.select("[abc]").first();
        //System.out.println(element);

        Elements elements1 = doc.select("[class=s_name]");
        for (Element e : elements1) {
            System.out.println(e.text());
        }
    }
    
    @Test
    public void testSelector2() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/resources/test.html"), "utf8");
        //el#id
        Element e1 = doc.select("div#test").first();
        System.out.println(e1.text());

        //el.class
        Elements es1 = doc.select("span.s_name");
        for (Element e2 : es1) {
            System.out.println(e2.text());
        }

        //el[attr]
        Element e3 = doc.select("span[abc]").first();
        System.out.println(e3);

        //el[attr]
        Element e4 = doc.select("span[abc].s_name").first();
        System.out.println(e4.text());
        Element e5 = doc.select("span[abc=123456789abc].s_name").first();
        System.out.println(e5);

        //父子关系查询

        //.city_con下的所有li元素，下的所有class为s_name的span元素
        Elements es2 = doc.select(".city_con li span.s_name");
        for (Element e6 : es2) {
            System.out.println(e6.text());
        }

        //parent > child 某个父元素下的直接子元素
        Elements es3 = doc.select(".city_con > li");
        System.out.println("****" + es3);

        Elements es4 = doc.select(".city_con > ul > li > a > span.s_name");
        System.out.println(es4);

        System.out.println("******************************************************");

        //parent > * 某个父元素下的所有直接子元素
        Elements es5 = doc.select(".city_con > ul > li > a > *");
        System.out.println(es5);
    }


}
