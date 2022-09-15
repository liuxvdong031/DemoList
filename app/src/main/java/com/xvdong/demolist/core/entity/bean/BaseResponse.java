package com.xvdong.demolist.core.entity.bean;

import java.util.List;

/**
 * Created by xvDong on 2022/9/15.
 */

public class BaseResponse<T> {
   public int code;
   public T data;
   public String message;
   public List<RepoBean> items;
}
