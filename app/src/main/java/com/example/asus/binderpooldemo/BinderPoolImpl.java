package com.example.asus.binderpooldemo;

import android.os.IBinder;
import android.os.RemoteException;

//连接池AIDL接口的实现
public class BinderPoolImpl extends IBinderPool.Stub {
    public BinderPoolImpl() {
        super();
    }

    //根据请求Code返回特定的binder对象--特定AIDL接口实现类，通过这个binder对象所执行的操作全部发生在远程服务端
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder=null;
        switch (binderCode){
            case BinderPool.BINDER_SECURITY_CENTER:{
                binder=new SecurityCenterImpl();
                break;
            }
            case BinderPool.BINDER_COMPUTE:{
                binder=new ComputeImpl();
                break;
            }
            default:
                break;

        }
        return binder;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
