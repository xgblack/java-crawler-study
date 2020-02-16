package cn.xgblack.crawler.controller;

import cn.xgblack.crawler.entity.JobInfo;
import cn.xgblack.crawler.service.JobInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 招聘信息(JobInfo)表控制层
 *
 * @author xgBLACK
 * @since 2020-02-16 20:19:50
 */
@RestController
@RequestMapping("jobInfo")
public class JobInfoController {
    /**
     * 服务对象
     */
    @Resource
    private JobInfoService jobInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JobInfo selectOne(Long id) {
        return this.jobInfoService.queryById(id);
    }

}