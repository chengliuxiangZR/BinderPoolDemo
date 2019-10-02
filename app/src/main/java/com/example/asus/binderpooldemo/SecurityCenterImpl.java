package com.example.asus.binderpooldemo;

import android.os.RemoteException;

//加解密AIDL接口的实现

public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE='^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars=content.toCharArray();
        for(int i=0;i<chars.length;i++){
            chars[i]^=SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
