package cn.xgblack.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xg BLACK
 * @date 2020/2/6 19:59
 * description:
 */
public class Crawler01 {
    public static void main(String[] args) throws IOException {
        //打开浏览器，创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //输入网址,get请求，HttpGet对象
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        //按回车，发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //解析响应，获取数据

        //判断状态码
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            String context = EntityUtils.toString(httpEntity, "utf-8");
            System.out.println(context);
        }
    }
}
