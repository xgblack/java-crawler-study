package cn.xgblack.crawler.controller;

import cn.xgblack.crawler.entity.JdItem;
import cn.xgblack.crawler.service.JdItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 京东商品表(JdItem)表控制层
 *
 * @author makejava
 * @since 2020-02-14 21:09:34
 */
@RestController
@RequestMapping("jdItem")
public class JdItemController {
    /**
     * 服务对象
     */
    @Resource
    private JdItemService jdItemService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JdItem selectOne(Long id) {
        return this.jdItemService.queryById(id);
    }

    @GetMapping("save")
    public void save(JdItem jdItem) {
        this.jdItemService.insert(jdItem);
    }
    @GetMapping("findAll")
    public List<JdItem> findAll(JdItem jdItem){
        return this.jdItemService.queryAll(jdItem);
    }

}