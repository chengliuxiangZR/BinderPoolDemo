package com.example.asus.binderpooldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    ISecurityCenter mSecurityCenter;
    ICompute iCompute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void myClick(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }
    public void doWork(){
        BinderPool binderPool=BinderPool.getInstance(MainActivity.this);

        IBinder securityBinder=binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter=(ISecurityCenter) SecurityCenterImpl.asInterface(securityBinder);

        Log.d(TAG,"visit ISecurityCenter");
        String msg="Helloworld-安卓";
        System.out.println("content:"+msg);
        try {
            String password=mSecurityCenter.encrypt(msg);
            System.out.println("encrypt:"+password);
            System.out.println("descript:"+mSecurityCenter.decrypt(password));
        }catch (RemoteException e){
            e.printStackTrace();
        }

        Log.d(TAG,"visit ICompute");

        IBinder computeBinder=binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        iCompute=(ICompute)ComputeImpl.asInterface(computeBinder);
        try {
            System.out.println("3+5= "+iCompute.add(3,5));
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }
}
