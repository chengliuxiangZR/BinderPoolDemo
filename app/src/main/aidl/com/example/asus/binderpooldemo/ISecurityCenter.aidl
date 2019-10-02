// ISecurityCenter.aidl
package com.example.asus.binderpooldemo;

// Declare any non-default types here with import statements

//加解密AIDL接口
interface ISecurityCenter {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     String encrypt(String content);
     String decrypt(String password);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
