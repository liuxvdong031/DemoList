package com.xvdong.demolist.core.binding;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构基类
 *
 * @author haibo.xin
 * @date 2022/3/22 8:07
 */
public class BaseTreeVO <T> {

   /**
    * 唯一标识id
    * @mock 1315325465811223
    */
   private Long id;

   /**
    * 父级唯一标识
    * @mock 1315325465811223
    */
   private Long parentId;

   /**
    * 子级集合
    */
   private List<T> children;

   /**
    * 数据版本
    * @mock 0
    */
   private Integer version;
   /**
    * 数据权限
    * @mock 1
    */
   private Long butPerm;


   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getParentId() {
      return parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public List<T> getChildren() {
      if (children == null) {
         return new ArrayList<>();
      }
      return children;
   }

   public void setChildren(List<T> children) {
      this.children = children;
   }

   public Integer getVersion() {
      return version;
   }

   public void setVersion(Integer version) {
      this.version = version;
   }

   public Long getButPerm() {
      return butPerm;
   }

   public void setButPerm(Long butPerm) {
      this.butPerm = butPerm;
   }
}