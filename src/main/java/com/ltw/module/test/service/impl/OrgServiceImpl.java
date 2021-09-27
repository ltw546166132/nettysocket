package com.ltw.module.test.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltw.module.test.entity.Org;
import com.ltw.module.test.mapper.OrgMapper;
import com.ltw.module.test.service.OrgService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org>
implements OrgService{

    @Override
    public List<Tree<Long>> tree(){
        List<Org> list = list();
        List<TreeNode<Long>> nodeList = list.stream().map(org -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("createTime", org.getCreateTime());
            map.put("ord", org.getOrd());
            TreeNode<Long> longTreeNode = new TreeNode<Long>().setId(org.getId()).setParentId(org.getParentId()).setName(org.getOrgName()).setWeight(org.getOrd()).setExtra(map);
            return longTreeNode;
        }).collect(Collectors.toList());

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("ord");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setChildrenKey("child");
        treeNodeConfig.setNameKey("orgName");
//        treeNodeConfig.setDeep(10);
        List<Tree<Long>> trees = TreeUtil.build(nodeList, 0L, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            tree.putExtra("createTime", treeNode.getExtra().getOrDefault("createTime", null));
            tree.putExtra("ord", treeNode.getExtra().getOrDefault("ord", 0));
        });
        System.out.println(trees);
        return trees;
    }

}




