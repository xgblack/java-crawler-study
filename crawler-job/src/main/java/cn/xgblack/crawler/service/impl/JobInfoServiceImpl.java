package cn.xgblack.crawler.service.impl;

import cn.xgblack.crawler.entity.JobInfo;
import cn.xgblack.crawler.mapper.JobInfoDao;
import cn.xgblack.crawler.service.JobInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 招聘信息(JobInfo)表服务实现类
 *
 * @author xgBLACK
 * @since 2020-02-16 20:19:46
 */
@Service("jobInfoService")
public class JobInfoServiceImpl implements JobInfoService {
    @Resource
    private JobInfoDao jobInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JobInfo queryById(Long id) {
        return this.jobInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<JobInfo> queryAllByLimit(int offset, int limit) {
        return this.jobInfoDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<JobInfo> queryAll(JobInfo jobInfo) {
        return this.jobInfoDao.queryAll(jobInfo);
    }

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
        //根据url查询数据
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        List<JobInfo> jobs = queryAll(param);
        if (jobs.size() == 0) {
            //如果数据不存在,需要新增
            this.jobInfoDao.insert(jobInfo);
        } else {
            //已存在，需要更新
            for (JobInfo job : jobs) {
                if (job.getTime() == null || !job.getTime().equals(jobInfo.getTime())) {
                    this.jobInfoDao.update(jobInfo);
                }
            }
        }
    }

    /**
     * 新增数据
     *
     * @param jobInfo 实例对象
     * @return 实例对象
     */
    @Override
    public JobInfo insert(JobInfo jobInfo) {
        this.jobInfoDao.insert(jobInfo);
        return jobInfo;
    }

    /**
     * 修改数据
     *
     * @param jobInfo 实例对象
     * @return 实例对象
     */
    @Override
    public JobInfo update(JobInfo jobInfo) {
        this.jobInfoDao.update(jobInfo);
        return this.queryById(jobInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.jobInfoDao.deleteById(id) > 0;
    }
}