package cn.xgblack.crawler.test;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xg BLACK
 * @date 2020/2/6 20:26
 * description:
 */
public class CrawlerConfigTest {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://blog.xgblack.cn");
        //配置请求信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)//获取连接的最长时间，单位ms
                .setConnectionRequestTimeout(500)//设置获取连接的最长时间，单位ms
                .setSocketTimeout(10 * 1000)//设置数据传输的最长时间，单位ms
                .build();
        //给请求设置请求信息
        httpGet.setConfig(config);
        
        System.out.println("发起请求的信息：" + httpGet);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);


            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
