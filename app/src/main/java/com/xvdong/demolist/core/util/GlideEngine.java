package com.xvdong.demolist.core.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xvdong.demolist.R;

import androidx.databinding.BindingAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Glide4.x的加载图片引擎实现,单例模式
 * Glide4.x的缓存机制更加智能，已经达到无需配置的境界。如果使用Glide3.x，需要考虑缓存机制。
 * Created by huan on 2018/1/15.
 */

public class GlideEngine {
    //单例
    private static GlideEngine instance = null;

    private final int defaultPlaceHolderRes = R.mipmap.ic_launcher;
    private final int defaultErrorRes = R.mipmap.ic_launcher_round;

    public static final String DEFAULT_IMAGE_URL = "https://pic.qqtn.com/up/2019-9/15690311636958128.jpg";


    //单例模式，私有构造方法
    private GlideEngine() {
    }

    //获取单例
    public static GlideEngine getInstance() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri       图片路径Uri
     * @param imageView 加载到的ImageView
     */
    //安卓10推荐uri，并且path的方式不再可用
    public void loadPhoto(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    /**
     * 加载gif动图图片到ImageView，gif动图不动
     *
     * @param context   上下文
     * @param gifUri    gif动图路径Uri
     * @param imageView 加载到的ImageView
     *                  <p>
     *                  备注：不支持动图显示的情况下可以不写
     */
    //安卓10推荐uri，并且path的方式不再可用
    public void loadGifAsBitmap(Context context, Uri gifUri, ImageView imageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView);
    }

    /**
     * 加载gif动图到ImageView，gif动图动
     *
     * @param context   上下文
     * @param gifUri    gif动图路径Uri
     * @param imageView 加载动图的ImageView
     *                  <p>
     *                  备注：不支持动图显示的情况下可以不写
     */
    //安卓10推荐uri，并且path的方式不再可用
    public void loadGif(Context context, Uri gifUri, ImageView imageView) {
        Glide.with(context).asGif().load(gifUri).transition(withCrossFade()).into(imageView);
    }

    /**
     * 获取图片加载框架中的缓存Bitmap
     *
     * @param context 上下文
     * @param uri     图片路径
     * @param width   图片宽度
     * @param height  图片高度
     * @return Bitmap
     * @throws Exception 异常直接抛出，EasyPhotos内部处理
     */
    //安卓10推荐uri，并且path的方式不再可用
    public Bitmap getCacheBitmap(Context context, Uri uri, int width, int height) throws Exception {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get();
    }


    public void displayImage(Object url, ImageView imageView) {
        displayImage(url, defaultPlaceHolderRes, defaultErrorRes, imageView);
    }

    public void displayImage(Object url, int placeholderRes, int errorRes, ImageView imageView) {
        Glide.with(Utils.getApp())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(placeholderRes == 0 ? defaultPlaceHolderRes : placeholderRes)
                        .error(errorRes == 0 ? defaultErrorRes : errorRes))
                .into(imageView);
    }

    /**
     * 显示圆形图片
     */
    public void displayCircleImage(String url, int placeholderRes, int errorRes, ImageView imageView) {
        Glide.with(Utils.getApp())
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(placeholderRes == 0 ? defaultPlaceHolderRes : placeholderRes)
                        .error(errorRes == 0 ? defaultErrorRes : errorRes)
                        .transform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 显示圆角图片
     */
    public void displayCornerImage(String url, int placeholderRes, int errorRes, ImageView imageView, int imgCorner) {
        if (imgCorner != 0) {
            Glide.with(Utils.getApp())
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(placeholderRes == 0 ? defaultPlaceHolderRes : placeholderRes)
                            .error(errorRes == 0 ? defaultErrorRes : errorRes))
                    .transform(new CenterCrop(), new RoundedCorners(SizeUtils.dp2px(imgCorner)))
                    .into(imageView);
        } else {
            displayImage(url, placeholderRes, errorRes, imageView);
        }

    }

    /**
     * 显示每个角都可以定义是否是圆角的图片
     */
    public void displayCustomCornerImage(String url, int placeholderRes, int errorRes, ImageView imageView, int imgCorner,
                                         boolean imgCornerTopLeft, boolean imgCornerTopRight, boolean imgCornerBottomLeft, boolean imgCornerBottomRight) {
        RoundedCornersTransform cornersTransform = new RoundedCornersTransform(imageView.getContext(), SizeUtils.dp2px(imgCorner));
        cornersTransform.setNeedCorner(imgCornerTopLeft, imgCornerTopRight, imgCornerBottomLeft, imgCornerBottomRight);
        Glide.with(Utils.getApp())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(placeholderRes == 0 ? defaultPlaceHolderRes : placeholderRes)
                        .error(errorRes == 0 ? defaultErrorRes : errorRes))
                .transform(new CenterCrop(), cornersTransform)
                .into(imageView);
    }

    /**
     * dataBinding 自定义XML属性的方法.
     */
    @BindingAdapter(value = {"url", "placeholderRes", "errorRes", "imgCorner", "imgCornerTopLeft", "imgCornerTopRight", "imgCornerBottomLeft", "imgCornerBottomRight", "isCircle"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes, int errorRes, int imgCorner, boolean imgCornerTopLeft, boolean imgCornerTopRight, boolean imgCornerBottomLeft, boolean imgCornerBottomRight, boolean isCirCle) {
        LogUtils.d("setImageUri");
        if (!TextUtils.isEmpty(url)) {
            if (isCirCle) {
                GlideEngine.getInstance().displayCircleImage(url, placeholderRes, errorRes, imageView);
            } else {
                if (imgCornerTopLeft || imgCornerBottomRight || imgCornerTopRight || imgCornerBottomLeft) {
                    GlideEngine.getInstance().displayCustomCornerImage(url, placeholderRes, errorRes, imageView, imgCorner,
                            imgCornerTopLeft, imgCornerTopRight, imgCornerBottomLeft, imgCornerBottomRight);
                } else if (imgCorner != 0) {
                    GlideEngine.getInstance().displayCornerImage(url, placeholderRes, errorRes, imageView, imgCorner);
                } else {
                    GlideEngine.getInstance().displayImage(url, placeholderRes, errorRes, imageView);
                }
            }
        }
    }

}
