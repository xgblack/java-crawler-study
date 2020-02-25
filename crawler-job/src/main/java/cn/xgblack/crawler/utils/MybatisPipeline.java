package cn.xgblack.crawler.utils;


import cn.xgblack.crawler.entity.JobInfo;
import cn.xgblack.crawler.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author xg BLACK
 * @date 2020/2/24 22:45
 * description:
 */
@Component
public class MybatisPipeline implements Pipeline {

    @Autowired
    private JobInfoService jobInfoService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        JobInfo jobInfo = resultItems.get("jobInfo");
        if (jobInfo != null) {
            jobInfoService.save(jobInfo);
        }
    }
}
