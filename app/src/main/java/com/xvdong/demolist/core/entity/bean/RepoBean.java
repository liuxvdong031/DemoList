package com.xvdong.demolist.core.entity.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xvDong on 2022/9/14.
 */

public class RepoBean {

   @SerializedName("id")
   private String id;
   @SerializedName("name")
   private String name;
   @SerializedName("description")
   private String description;
   @SerializedName("stargazers_count")
   private String starCount;

   public String getId() {
      return id == null ? "" : id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name == null ? "" : name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description == null ? "" : description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getStarCount() {
      return starCount == null ? "" : starCount;
   }

   public void setStarCount(String starCount) {
      this.starCount = starCount;
   }
}
