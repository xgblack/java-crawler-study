package cn.xgblack.crawler.task;

import cn.xgblack.crawler.entity.JobInfo;
import cn.xgblack.crawler.utils.MathSalary;
import cn.xgblack.crawler.utils.MybatisPipeline;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author xg BLACK
 * @date 2020/2/16 21:02
 * description:
 */
@Component
public class JobProcessor implements PageProcessor {

    private String url = "https://search.51job.com/list/000000,000000,0000,01%252C38%252C32,9,99,java,2,1.html"
            + "?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99"
            + "&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address="
            + "&line=&specialarea=00&from=&welfare=";

    @Override
    public void process(Page page) {
        //解析页面，获取招聘信息详情的URL地址
        List<Selectable> list = page.getHtml().css("div#resultList > div.el").nodes();
        //System.out.println(list.size());

        if (list.size() == 0) {
            //如果为空，表示这是招聘详情页
            //解析页面，获取招聘信息，保存数据
            this.saveJobInfo(page);
        } else {
            //如果不为空，表示是列表页，获取详情页地址，并放入任务队列
            for (Selectable selectable : list) {
                String jobInfoUrl = selectable.css("p.t1 > span").links().toString();
                //System.out.println(jobInfoUrl);
                //把获取到的URL地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取下一页的URL
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            //System.out.println(bkUrl);
            //放到任务队列
            page.addTargetRequest(bkUrl);
        }

    }

    /**
     * 获取招聘信息，保存数据
     * @param page
     */
    private void saveJobInfo(Page page) {
        //获取招聘详情对象
        JobInfo jobInfo = new JobInfo();
        //解析页面
        Html html = page.getHtml();
        //获取数据，封装到对象中
        jobInfo.setCompanyName(html.css("div.tHeader div.cn p.cname a","text").toString());
        //地址处理
        String companyAddr = null;
        try {
            companyAddr = Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text();
        } catch (IndexOutOfBoundsException e) {
            companyAddr = "未查找到";
        }
        jobInfo.setCompanyAddr(companyAddr);
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
        jobInfo.setJobName(html.css("div.tHeader div.cn h1","text").toString());
        String[] msgs = html.css("div.tHeader div.cn p.msg", "title").toString().split("\\|");
        jobInfo.setJobAddr(msgs[0].trim());
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());
        //获取薪资
        Integer[] salary = MathSalary.getSalary(html.css("div.tHeader div.cn strong", "text").toString());
        jobInfo.setSalaryMax(salary[1]);
        jobInfo.setSalaryMin(salary[0]);

        //处理发布时间
        String time = null;
        for (String msg : msgs) {
            if (msg.contains("发布")) {
                time = msg.trim().substring(0, msg.length() - 2).trim();
                break;
            }
        }
        if (time == null) {
            time = "未知";
        }
        jobInfo.setTime(time);

        //结果保存下来
        page.putField("jobInfo",jobInfo);
    }

    private Site site = Site.me()
            .setCharset("GBK")//设置编码
            .setTimeOut(10 * 1000)//设置超时时间
            .setSleepTime(3000)//设置重试的间隔时间
            .setRetrySleepTime(3);//设置重试次数

    @Autowired
    private MybatisPipeline mybatisPipeline;

    @Override
    public Site getSite() {
        return this.site;
    }

    //initialDelay当项目启动后多久执行任务
    //fixedDelay每间隔多少时间执行
    @Scheduled(initialDelay = 1000,fixedDelay = 100 * 1000)
    public void process(){
        Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .addPipeline(mybatisPipeline)
                .run();

    }
}
