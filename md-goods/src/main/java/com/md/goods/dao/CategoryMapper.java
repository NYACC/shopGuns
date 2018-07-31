package com.md.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.md.goods.model.Category;
import com.stylefeng.guns.core.node.ZTreeNode;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category>{

    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

}
