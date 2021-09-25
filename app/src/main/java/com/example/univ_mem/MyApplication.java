package com.example.univ_mem;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.litepal.LitePal;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/4/12.
 * TIME : 11:36.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static Context context;
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        LitePal.initialize(this);
        //得到应用程序级别的Context
        context = getApplicationContext();
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；
        // 传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "6141a5f233d9774a4ad24536", "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "b2412e876fc62756f93df7566e93e2bd");

        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        /**
         * 初始化夜间模式
         */
        // 默认设置为日间模式
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 用于工具类实例化，UI不能使用，使用后无法被回收，造成内存泄漏
     * @return
     */
    public static Context getApplication() {
        return instance;
    }

}
