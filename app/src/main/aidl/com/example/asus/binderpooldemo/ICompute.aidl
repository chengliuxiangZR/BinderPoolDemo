// ICompute.aidl
package com.example.asus.binderpooldemo;

// Declare any non-default types here with import statements

//计算AIDL接口
interface ICompute {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int add(int a,int b);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
