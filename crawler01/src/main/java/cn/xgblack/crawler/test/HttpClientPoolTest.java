package cn.xgblack.crawler.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xg BLACK
 * @date 2020/2/6 21:17
 * description:
 */
public class HttpClientPoolTest {
    public static void main(String[] args) {
        //创建连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        //使用连接池管理器发起请求
        doGet(cm);
        doPost(cm);
    }

    private static void doPost(PoolingHttpClientConnectionManager cm) {

    }

    private static void doGet(PoolingHttpClientConnectionManager cm) {
        //不是每次创建HttpClient，而是通过管理器获取
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet("http://blog.xgblack.cn");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //不能关闭httpClient
        }
    }
}
