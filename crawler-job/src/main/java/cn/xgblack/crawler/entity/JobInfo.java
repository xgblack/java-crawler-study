package cn.xgblack.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 招聘信息(JobInfo)实体类
 *
 * @author xgBLACK
 * @since 2020-02-16 20:19:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo implements Serializable {
    private static final long serialVersionUID = 346118877599101857L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 公司名称
    */
    private String companyName;
    /**
    * 公司联系方式
    */
    private String companyAddr;
    /**
    * 公司信息
    */
    private String companyInfo;
    /**
    * 职位名称
    */
    private String jobName;
    /**
    * 工作地点
    */
    private String jobAddr;
    /**
    * 职位信息
    */
    private String jobInfo;
    /**
    * 薪资范围，最小
    */
    private Integer salaryMin;
    /**
    * 薪资范围，最大
    */
    private Integer salaryMax;
    /**
    * 招聘信息详情页
    */
    private String url;
    /**
    * 职位最近发布时间
    */
    private String time;

}