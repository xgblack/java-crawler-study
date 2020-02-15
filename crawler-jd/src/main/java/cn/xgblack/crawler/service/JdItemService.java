package cn.xgblack.crawler.service;

import cn.xgblack.crawler.entity.JdItem;
import java.util.List;

/**
 * 京东商品表(JdItem)表服务接口
 *
 * @author makejava
 * @since 2020-02-14 21:09:29
 */
public interface JdItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JdItem queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<JdItem> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param jdItem 实例对象
     * @return 对象列表
     */
    List<JdItem> queryAll(JdItem jdItem);

    /**
     * 新增数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    JdItem insert(JdItem jdItem);

    /**
     * 修改数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    JdItem update(JdItem jdItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}