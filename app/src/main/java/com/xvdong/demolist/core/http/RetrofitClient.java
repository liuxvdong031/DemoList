package com.xvdong.demolist.core.http;

import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xvdong.demolist.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xvDong on 2019/6/14.
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static final String APP_VERSION = "App-Version";
    private static final String PLATFORM = "Platform";
    private static final String TOKEN = "Token";
    private static final String TERMINAL = "Terminal";
    private static final String TAG = "HttpClient";
    private static RetrofitClient sHttpClient;
    private final int CONNECT_TIME_OUT = 30;
    private Retrofit mRetrofitClient;
    private final String mAppVersionName;

    Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.header(TERMINAL, "kksapp")
                    .header(TOKEN, "")
                    .header(APP_VERSION, mAppVersionName)
                    .header(PLATFORM, "android");
            Request build = builder.build();
            return chain.proceed(build);
        }
    };

    private RetrofitClient() {
        mAppVersionName = AppUtils.getAppVersionName();
        mRetrofitClient = createRetrofitClient();
    }

    public static RetrofitClient getInstance() {
        if (sHttpClient == null) {
            synchronized (RetrofitClient.class) {
                if (sHttpClient == null) {
                    sHttpClient = new RetrofitClient();
                }
            }
        }
        return sHttpClient;
    }

    private HttpLoggingInterceptor HttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            if (BuildConfig.DEBUG) {
                longLog(TAG, message);
            }
        });
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    private Interceptor getInterceptor() {
        return mInterceptor;
    }

    private OkHttpClient getOkHttpClient() {
            return new OkHttpClient().newBuilder()
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(HttpLoggingInterceptor())
                    .addInterceptor(getInterceptor())
                    .build();
    }

    private Retrofit createRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T getApiService(Class<T> cl) {
        return mRetrofitClient.create(cl);
    }



    /**
     * 截断输出日志
     *
     * @param msg
     */
    private static void longLog(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0) {
            return;
        }
        int segmentSize = 3 * 1024;
        long length = msg.length();
        // 长度小于等于限制直接打印
        if (length <= segmentSize) {
            Log.i(tag, msg);
        } else {
            // 循环分段打印日志
            while (msg.length() > segmentSize) {
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            // 打印剩余日志
            Log.i(tag, msg);
        }
    }

    /**
     * 设置订阅 和 所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)//请求失败重连次数
                .subscribe(s);
    }

}
