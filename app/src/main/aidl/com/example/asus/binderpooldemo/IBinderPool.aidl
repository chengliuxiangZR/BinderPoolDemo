// IBinderPool.aidl
package com.example.asus.binderpooldemo;

// Declare any non-default types here with import statements

//连接池AIDL接口
interface IBinderPool {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     IBinder queryBinder(int binderCode);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
