package com.example.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.example.univ_mem.MyGlideEngine;
import com.example.univ_mem.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/5/19.
 * TIME : 10:31.
 */

public class PictureUtil {
    public static void SelectPictures(Context context,int REQUEST_CODE) {
        Matisse
                .from((Activity) context)
                .choose(MimeType.ofAll())//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(9)//最大选择数量为9
                .gridExpectedSize(120)//图片显示表格的大小getResources()
                //.captureStrategy(new CaptureStrategy(true, "com.example.seeker.fileprovider"))
                //.capture(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向。
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//加载方式
                .forResult(REQUEST_CODE);//请求码
    }
}
