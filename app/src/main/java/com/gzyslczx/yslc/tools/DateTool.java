package com.gzyslczx.yslc.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {

    public static final String y_M_d="yyyy-MM-dd";
    public static final String y_M_d_Hms="yyyy-MM-dd HH:mm:ss";
    public static final String yMdHm = "yyyyMMddHHmm";

    private static volatile DateTool dateTool;

    public static DateTool instance(){
        if (dateTool==null){
            synchronized (DateTool.class){
                if (dateTool==null){
                    dateTool = new DateTool();
                }
            }
        }
        return dateTool;
    }

    public String GetToday(String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    public boolean OverTimeNow(String oldDate, int hour) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(y_M_d_Hms);
        Date start = sdf.parse(oldDate);
        Date end = sdf.parse(GetToday(y_M_d_Hms));
        long cha = end.getTime() - start.getTime();
        if(cha<0){
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result<=hour){
            return true;
        }
        return false;
    }

}
