package com.ltw.module.test.service;

import cn.hutool.core.lang.tree.Tree;
import com.ltw.module.test.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface OrgService extends IService<Org> {

    List<Tree<Long>> tree();
}
