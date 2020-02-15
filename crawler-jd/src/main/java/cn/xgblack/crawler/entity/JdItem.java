package cn.xgblack.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 京东商品表(JdItem)实体类
 *
 * @author makejava
 * @since 2020-02-14 21:09:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdItem implements Serializable {
    private static final long serialVersionUID = -41827931978978651L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 商品集合id
    */
    private Long spu;
    /**
    * 商品最小品类单元id
    */
    private Long sku;
    /**
    * 商品标题
    */
    private String title;
    /**
    * 商品价格
    */
    private Double price;
    /**
    * 商品图片
    */
    private String pic;
    /**
    * 商品详情地址
    */
    private String url;
    /**
    * 创建时间
    */
    private Date created;
    /**
    * 更新时间
    */
    private Date updated;


}