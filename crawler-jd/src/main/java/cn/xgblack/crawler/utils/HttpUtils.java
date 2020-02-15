package cn.xgblack.crawler.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author xg BLACK
 * @date 2020/2/14 21:37
 * description:
 */
@Component
public class HttpUtils {
    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        this.cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     * @param url 链接
     * @return HTML字符串
     */
    public String doGetHtml(String url){
        //获取HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //设置HTTPGet对象
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());
        //设置请求头模拟浏览器
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        httpGet.addHeader("Cookie", "__jda=122270672.1581680541214214927388.1581680541.1581741873.1581749466.4; __jdc=122270672; __jdv=76161171|baidu|-|organic|not set|1581680541215; __jdu=1581680541214214927388; areaId=12; ipLoc-djd=12-919-920-0; PCSYCityID=CN_320000_320700_320707; shshshfp=770b6afd472ab021b49d20a771c74384; shshshfpa=7a165a3c-50e3-f19a-d72c-a5bfa7304f0c-1581680543; shshshfpb=d7JEFL6LubAoP1uLFp7fglA%3D%3D; xtest=3574.cf6b6759; qrsc=3; rkv=V0100; 3AB9D23F7A4B3C9B=7D5KUJUDSCE4AGRIN2AMZB6DK5PQG7TVYQU7ZQ27NX44D7SDLE3CJBJYWCUNXHD5RPIOSHHCZJNGKT3A2S54PRDXGY; wlfstk_smdl=inu14gxngedrssan5ehjv1md4hi4uc1d; _pst=jd_6c59bc0c6c7bf; logintype=qq; unick=%E5%B0%8F%E5%85%89xgblack; pin=jd_6c59bc0c6c7bf; npin=jd_6c59bc0c6c7bf; _tp=Ydg6syaE3x46AFYQ59nCW%2BwB6v9GKJd9mg3pvL5pKeU%3D; pinId=xkV2E_YtPKRvGHaGj5nNsLV9-x-f3wj7; thor=B07E92D6D41655EDFE91DFAC4405FB9EFA17C9AF2ED464622670B65C4401A53F36A6781635F61D5FC6AE40FB1FDBECCF96099A7976C25A08B38A6BF0F70839082AA610A246169E2AA5DDA26131A74C7CB2D846AEC598B13929D4E2EAB65E76EEE992B63CD3F767EBE3EE318AA3B491FCB0CF0EAAFBC877905DD474723F0BE4F70FB33EA675031B9A575F157167F9646CE031C3E08D8074416C3AEC3DDA15D2DA; user-key=8ff36040-8eda-48aa-9415-c4f2a983103c; cn=4; __jdb=122270672.2.1581680541214214927388|4.1581749466");


        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否为空
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
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
        }
        //返回空字符串
        return "";
    }



    /**
     * 下载图片
     * @param url 链接
     * @return 图片名称
     */
    public String doGetImage(String url) {
        //获取HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //CloseableHttpClient httpClient = HttpClients.custom().build();
        //设置HTTPGet对象
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());
        //设置请求头模拟浏览器
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
        httpGet.addHeader("Cookie", "__jda=122270672.1581680541214214927388.1581680541.1581741873.1581749466.4; __jdc=122270672; __jdv=76161171|baidu|-|organic|not set|1581680541215; __jdu=1581680541214214927388; areaId=12; ipLoc-djd=12-919-920-0; PCSYCityID=CN_320000_320700_320707; shshshfp=770b6afd472ab021b49d20a771c74384; shshshfpa=7a165a3c-50e3-f19a-d72c-a5bfa7304f0c-1581680543; shshshfpb=d7JEFL6LubAoP1uLFp7fglA%3D%3D; xtest=3574.cf6b6759; qrsc=3; rkv=V0100; 3AB9D23F7A4B3C9B=7D5KUJUDSCE4AGRIN2AMZB6DK5PQG7TVYQU7ZQ27NX44D7SDLE3CJBJYWCUNXHD5RPIOSHHCZJNGKT3A2S54PRDXGY; wlfstk_smdl=inu14gxngedrssan5ehjv1md4hi4uc1d; _pst=jd_6c59bc0c6c7bf; logintype=qq; unick=%E5%B0%8F%E5%85%89xgblack; pin=jd_6c59bc0c6c7bf; npin=jd_6c59bc0c6c7bf; _tp=Ydg6syaE3x46AFYQ59nCW%2BwB6v9GKJd9mg3pvL5pKeU%3D; pinId=xkV2E_YtPKRvGHaGj5nNsLV9-x-f3wj7; thor=B07E92D6D41655EDFE91DFAC4405FB9EFA17C9AF2ED464622670B65C4401A53F36A6781635F61D5FC6AE40FB1FDBECCF96099A7976C25A08B38A6BF0F70839082AA610A246169E2AA5DDA26131A74C7CB2D846AEC598B13929D4E2EAB65E76EEE992B63CD3F767EBE3EE318AA3B491FCB0CF0EAAFBC877905DD474723F0BE4F70FB33EA675031B9A575F157167F9646CE031C3E08D8074416C3AEC3DDA15D2DA; user-key=8ff36040-8eda-48aa-9415-c4f2a983103c; cn=4; __jdb=122270672.2.1581680541214214927388|4.1581749466");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否为空
                if (response.getEntity() != null) {
                    //下载图片
                    //获取图片后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建文件名，重命名图片
                    String picName = UUID.randomUUID().toString() + extName;
                    //下载图片
                    //声明OutputStream
                    OutputStream outputStream = new FileOutputStream(new File("D:/下载/test/images/" + picName));
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;
                }
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
        }
        //下载失败，返回空字符串
        return "";
    }


    //设置请求信息
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                //创建链接的最长时间
                .setConnectTimeout(1000)
                //获取连接的最长时间
                .setConnectionRequestTimeout(500)
                //数据传输的最长时间
                .setSocketTimeout(10000)
                .build();
        return config;
    }
}
