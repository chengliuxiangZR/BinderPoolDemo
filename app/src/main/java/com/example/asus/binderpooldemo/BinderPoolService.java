package com.example.asus.binderpooldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

//远程服务的实现,运行在单独进程
//并没有为每个AIDL单独创建Service
//只为BinderPool创建Service
public class BinderPoolService extends Service {
    private static final String TAG="BinderPoolService";
    private Binder mBinderPool=new BinderPoolImpl();
    public BinderPoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinderPool;
    }
}
