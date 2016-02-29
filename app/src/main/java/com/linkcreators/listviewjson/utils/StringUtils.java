package com.linkcreators.listviewjson.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * Created by Jun  2015/10/30.
 */

public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static SimpleDateFormat dateFormater = new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private final static SimpleDateFormat dateFormater2 = new
    // SimpleDateFormat("yyyy-MM-dd");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 字符串转JSON
     * @param json
     * @return
     * @throws JSONException
     */
    public static JSONObject toJSONObject(String json) throws JSONException{
        if(!isEmpty(json)){
            if(json.indexOf("[") == 0){
                json = json.substring(1, json.length());
            }
            if(json.lastIndexOf("]") == json.length()){
                json = json.substring(0, json.length() -1);
            }
            return new JSONObject(json);
        }
        return null;
    }

    /**
     * 字符串转JSON
     * @param json
     * @return
     * @throws JSONException
     */
    public static JSONArray toJSONArray(String json) throws JSONException{
        if(!isEmpty(json)){
//            if(json.indexOf("[") == 0){
//                json = json.substring(1, json.length());
//            }
//            if(json.lastIndexOf("]") == json.length()){
//                json = json.substring(0, json.length() -1);
//            }
//            return new JSONArray(json);
        }
        return new JSONArray(json);
    }

    /**
     * 将字符串转为日期类型
     * @param stringDate
     * @return
     */
    public static Date toDate(String stringDate){
        try{
            return dateFormater.get().parse(stringDate);
        }catch (ParseException e){
            return null;
        }
    }

    /**
     * 时间戳转换
     * @param timeStampString
     * @return
     */
    public static String TimeStamp2Date(String timeStampString){
        Long timeStamp = Long.parseLong(timeStampString) * 1000;
        return dateFormater.get().format(new Date(timeStamp));
    }

    /**
     * 以友好的方式显示距离
     * @param distance
     * @return
     */
    public static String FriendlyDistance(double distance){
        String dis = "";
        if(distance >= 1000){
            if(distance / 1000 >= 100){
                dis = ">100km";
            }else{
                dis = String.format("%.1f",(distance / 1000)) + "km";
            }
        }else{
            dis = distance + "m";
        }
        //Log.v("以友好的方式显示距离", distance + "   " + dis);
        return dis;
    }

    /**
     * 以友好的方式显示时间
     * @param stringDate
     * @return
     */
    public static String friendlyTime(String stringDate){
        Date time = toDate(stringDate);
        if(null == time){
            return "Unknown";
        }
        String flagTime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if(curDate.equals(paramDate)){
            int hour = (int) (cal.getTimeInMillis() - time.getTime() / 3600000);
            if(0 == hour){
                flagTime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            }else{
                flagTime = hour + "小时前";
            }
            return flagTime;
        }

        //毫秒计算天数 1day = （24h * 60min * 60s * 1000）ms
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if(0 == days){
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if(0 == hour){
                flagTime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            }else{
                flagTime = hour + "小时前";
            }
        }else if(1 == days){
            flagTime = "昨天";
        }else if(2 == days){
            flagTime = "前天";
        }else if(days > 2 && days <= 10){
            flagTime = days + "天前";
        }else if(days > 10){
            flagTime = dateFormater2.get().format(time);
        }
        return flagTime;
    }

    /**
     * 判断给定字符串时间是否为今日
     * @param stringDate
     * @return boolean
     */
    public static boolean isToday(String stringDate){
        Date time = toDate(stringDate);
        Date today = new Date();
        if(null != time){
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if(nowDate.equals(timeDate)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给定字符串是否空白串;
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串;
     * 若输入字符串为null或空字符串，返回true
     * @param inputString
     * @return boolean
     */
    public static boolean isEmpty(String inputString) {
        if(null == inputString || "".equals(inputString)){
            return true;
        }
        for(int i = 0; i < inputString.length(); i++){
            char ch = inputString.charAt(i);
            if(ch != ' ' && ch != '\t' && ch != '\r' && ch != '\n'){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * @param emailString
     * @return
     */
    public static boolean isEmailValid(String emailString){
        if(null == emailString || 0 == emailString.trim().length()){
            return false;
        }
        return emailer.matcher(emailString).matches();
    }

    /**
     * 检查手机号的格式
     * @param phoneNumber
     * @return 是否是有效的手机号
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|" +
                "(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|" +
                "(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 检查密码格式
     * @param password 你要设置的密码
     * @return 是否符合密码设置的要求
     */
    public static boolean isRightInputPwd(String password) {
        //密码至少为8位字母数字的组合
        // String isRight = "(?![a-z]+$|[0-9]+$)^[a-zA-Z0-9]{8,}$";
        // 6~16非空格
        String isRight = "[^ ]{6,16}";
        Pattern p = Pattern.compile(isRight);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 字符串转整数
     * @param str
     * @param defValue
     * @return
     */
    public static int StringToInt(String str, int defValue){
        try{
            return Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 对象转整数
     * @param obj
     * @return 转换异常返回 0
     */
    public static int ObjectToInt(Object obj){
        if(null == obj){
            return 0;
        }
        return StringToInt(obj.toString(), 0);
    }

    /**
     * 字符串转 长整数
     * @param str
     * @return 转换异常返回 0
     */
    public static long StringToLong(String str){
        try{
            return Long.parseLong(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     * @param str
     * @return 转换异常返回 false
     */
    public static boolean StringToBool(String str){
        try{
            return Boolean.parseBoolean(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 整型转字符串值
     * @param num
     * @return 转换返回字符串
     */
    public static String IntToString(int num){
        return  String.valueOf(num);
    }

}
