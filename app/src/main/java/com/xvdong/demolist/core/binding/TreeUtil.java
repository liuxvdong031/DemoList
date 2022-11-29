package com.xvdong.demolist.core.binding;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 递归公共方法
 *
 * @author haibo.xin
 * @date 2022/3/22 8:08
 */
public class TreeUtil {

    /**
     * 递归公共类
     * @date 2022-02-10
     * @author haibo.xin
     * @param trees 递归数据集
     * @return {@link List}
     */
    public static <T, U extends BaseTreeVO<U>> List<U> tree(List<U> trees) {

        return trees.stream().filter(m -> m.getParentId() == 0).peek(
                p -> p.setChildren(getChildren(p, trees))
        ).collect(Collectors.toList());
    }

    /**
     * 递归公共类
     * @date 2022/10/17
     * @author haibo.xin
     * @param trees 递归数据集
     * @param parentId 退出递归条件
     * @return {@link java.util.List}
     */
    public static <T, U extends BaseTreeVO<U>> List<U> tree(List<U> trees, long parentId) {
        return trees.stream().filter(m -> m.getParentId() == parentId).peek(
                p -> p.setChildren(getChildren(p, trees))
        ).collect(Collectors.toList());
    }

    /**
     * 递归查询子节点
     * @date 2022-02-10
     * @author haibo.xin
     * @param root 根节点
     * @param all  所有节点
     * @return {@link List}
     */
    private static <U extends BaseTreeVO<U>> List<U> getChildren(U root, List<U> all) {
        return all.stream().filter(m -> Objects.equals(m.getParentId(), root.getId())).peek(
                p -> p.setChildren(getChildren(p, all))
        ).collect(Collectors.toList());
    }
}