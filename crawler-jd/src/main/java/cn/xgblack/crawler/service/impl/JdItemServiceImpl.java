package cn.xgblack.crawler.service.impl;

import cn.xgblack.crawler.entity.JdItem;
import cn.xgblack.crawler.dao.JdItemDao;
import cn.xgblack.crawler.service.JdItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 京东商品表(JdItem)表服务实现类
 *
 * @author makejava
 * @since 2020-02-14 21:09:31
 */
@Service("jdItemService")
public class JdItemServiceImpl implements JdItemService {
    @Resource
    private JdItemDao jdItemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JdItem queryById(Long id) {
        return this.jdItemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<JdItem> queryAllByLimit(int offset, int limit) {
        return this.jdItemDao.queryAllByLimit(offset, limit);
    }


    @Override
    public List<JdItem> queryAll(JdItem jdItem) {
        return this.jdItemDao.queryAll(jdItem);
    }

    /**
     * 新增数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional//开启事务
    public JdItem insert(JdItem jdItem) {
        this.jdItemDao.insert(jdItem);
        return jdItem;
    }

    /**
     * 修改数据
     *
     * @param jdItem 实例对象
     * @return 实例对象
     */
    @Override
    public JdItem update(JdItem jdItem) {
        this.jdItemDao.update(jdItem);
        return this.queryById(jdItem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.jdItemDao.deleteById(id) > 0;
    }
}