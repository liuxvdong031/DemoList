package com.example.qihang.draw;

import android.content.Intent;
import android.os.Bundle;

import com.example.qihang.R;

import androidx.appcompat.app.AppCompatActivity;
/**
 * 启航的自定义view三部曲之绘制篇
 */
public class DrawMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_main);
        //概述及基本几何图形绘制
        findViewById(R.id.btn1).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart1Activity.class));
        });
        //路径及文字
        findViewById(R.id.btn2).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart2Activity.class));
        });
        //区域（Range）
        findViewById(R.id.btn3).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart3Activity.class));
        });
        //canvas变换与操作
        findViewById(R.id.btn4).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart4Activity.class));
        });
        //drawText()详解
        findViewById(R.id.btn5).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart5Activity.class));
        });
        //Path之贝赛尔曲线和手势轨迹、水波纹效果
        findViewById(R.id.btn6).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart6Activity.class));
        });
        //Paint之函数大汇总
        findViewById(R.id.btn7).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart7Activity.class));
        });
        //Paint之ColorMatrix与滤镜效果
        findViewById(R.id.btn8).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart8Activity.class));
        });
        //Paint之setColorFilter
        findViewById(R.id.btn9).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart9Activity.class));
        });
        //Paint之setXfermode(一)
        findViewById(R.id.btn10).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart10Activity.class));
        });
        //Paint之setXfermode(二)
        findViewById(R.id.btn11).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart11Activity.class));
        });
        //Paint之setXfermode(三)
        findViewById(R.id.btn12).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart12Activity.class));
        });
        //Canvas与图层(一)
        findViewById(R.id.btn13).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart13Activity.class));
        });
        //Canvas与图层（二）
        findViewById(R.id.btn14).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart14Activity.class));
        });
        //QQ红点拖动删除效果实现（基本原理篇）
        findViewById(R.id.btn15).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart15Activity.class));
        });
        //给控件添加阴影效果与发光效果
        findViewById(R.id.btn16).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart16Activity.class));
        });
        //为Bitmap添加阴影并封装控件
        findViewById(R.id.btn17).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart17Activity.class));
        });
        //BitmapShader与望远镜效果
        findViewById(R.id.btn18).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart18Activity.class));
        });
        //LinearGradient与闪动文字效果
        findViewById(R.id.btn19).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart19Activity.class));
        });
        //RadialGradient与水波纹按钮效果
        findViewById(R.id.btn20).setOnClickListener(view -> {
            startActivity(new Intent(this, DrawPart20Activity.class));
        });
    }
}