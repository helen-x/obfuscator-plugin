package com.helen.obfuscatorplugin;


import com.helen.obfuscator.IObfuscateKeepAll;

public class TestInterfaceKeep implements IObfuscateKeepAll {

    public String sName;
    public static String sSName;
    private int iValue;
    private static int iSValue;


    private void tFunc() {
    }

    public boolean isbValue() {
        return bValue;
    }

    public void setbValue(boolean bValue) {
        this.bValue = bValue;
    }

    protected boolean bValue;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public static String getsSName() {
        return sSName;
    }

    public static void setsSName(String sSName) {
        TestInterfaceKeep.sSName = sSName;
    }

    public int getiValue() {
        return iValue;
    }

    public void setiValue(int iValue) {
        this.iValue = iValue;
    }

    public static int getiSValue() {
        return iSValue;
    }

    public static void setiSValue(int iSValue) {
        TestInterfaceKeep.iSValue = iSValue;
    }
}
