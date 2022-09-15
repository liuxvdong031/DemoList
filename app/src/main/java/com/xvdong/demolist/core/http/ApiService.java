package com.xvdong.demolist.core.http;

import com.xvdong.demolist.core.entity.bean.BaseResponse;
import com.xvdong.demolist.core.entity.bean.RepoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xvDong on 2022/9/14.
 */

public interface ApiService {
    /**
     * 搜索github的项目
     * https://api.github.com/search/repositories?sort=stars&q=Android&per_page=5&page=1
     */
    @GET("search/repositories")
    Observable<BaseResponse<RepoBean>> getRepoList(@Query("sort") String sort,
                                                   @Query("q") String key,
                                                   @Query("per_page") int per_page,
                                                   @Query("page") int page);
}
