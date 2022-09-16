package com.gzyslczx.yslc.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTool {

    private volatile static SpTool spTool;
    private static SharedPreferences preferences;
    public static final String TAG = "SpTool";
    public static final String name = "GBbSpName";
    public static final String YRToken = "YRToken";
    public static final String YRTokenTime = "YRTTime";


    private SpTool(Context context){
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void Init(Context context){
        if (spTool==null){
            synchronized (SpTool.class){
                if (spTool==null){
                    PrintTool.PrintLogD(TAG, "初始化SpTool");
                    spTool = new SpTool(context);
                }
            }
        }
        PrintTool.PrintLogD(TAG, "SpTool已初始化");
    }

    public static boolean SaveInfo(String k, String v){
        if (spTool!=null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(k, v);
            return editor.commit();
        }
        PrintTool.PrintLogD(TAG, "SpTool Not Init");
        return false;
    }

    public static String GetInfo(String k){
        if (spTool!=null){
            String value = preferences.getString(k, TAG);
            if (!TAG.equals(value)){
                return  value;
            }
        }
        PrintTool.PrintLogD(TAG, "SpTool Not Init");
        return null;
    }



}
