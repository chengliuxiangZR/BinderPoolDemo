package com.example.asus.binderpooldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

//连接池的具体实现
//提高AIDL的开发效率，避免大量的Service创建
public class BinderPool {
    private static final String TAG="BinderPool";
    public static final int BINDER_NONE=-1;
    public static final int BINDER_COMPUTE=0;
    public static final int BINDER_SECURITY_CENTER=1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    //将bindService这一异步操作转换为同步操作
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    public BinderPool(Context context) {
        mContext=context.getApplicationContext();
        connectBinderPoolService();
    }
    //连接池单例
    public static BinderPool getInstance(Context context){
        if(sInstance==null){
            synchronized (BinderPool.class){
                if(sInstance==null){
                    sInstance=new BinderPool(context);
                }
            }
        }
        return sInstance;
    }
    //连接服务
    private synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch=new CountDownLatch(1);
        Intent service=new Intent(mContext,BinderPoolService.class);
//        service.setPackage("com.example.asus.binderpooldemo");
        mContext.bindService(service,mBinderPoolConnection,Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //利用binderPool的实现类来实现获取指定的binder
    public IBinder queryBinder(int binderCode){
        IBinder binder=null;
        try {
            if(mBinderPool!=null){
                binder=mBinderPool.queryBinder(binderCode);
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return binder;
    }
    //包含了AIDL的断线重连机制
    private ServiceConnection mBinderPoolConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool=IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            }catch (RemoteException e){
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //实现断线重连
    private IBinder.DeathRecipient mBinderPoolDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG,"binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            mBinderPool=null;
            //断线后重新连接远程服务
            connectBinderPoolService();
        }
    };
}
