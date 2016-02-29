package com.linkcreators.listviewjson.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Jun on 2015/11/25.
 */
public class URLs {

    public final static String HOST = "m.bitauto.com";
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";
    private final static String URL_SPLITTER = "/";
    private final static String URL_UNDERLINE = "_";

    private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
    public final static String NEWS_LIST = URL_API_HOST+"/appapi/News/List.ashx/";

    private int objId;
    private String objKey = "";
    private int objType;

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public String getObjKey() {
        return objKey;
    }

    public void setObjKey(String objKey) {
        this.objKey = objKey;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    /**
     * 解析url获得objId
     * @param path
     * @param urlType
     * @return
     */
    private final static String parseObjId(String path, String urlType){
        String objId;
        int objPath;
        String str;
        String[] temp;

        objPath = path.indexOf(urlType) + urlType.length();
        str = path.substring(objPath);
        if(str.contains(URL_SPLITTER)){
            temp = str.split(URL_SPLITTER);
            objId = temp[0];
        }else{
            objId = str;
        }
        return objId;
    }

    /**
     * 解析url获得objKey
     * @param path
     * @param urlType
     * @return
     */
    private final static String parseObjKey(String path, String urlType){
        try {
            //对path进行解码处理
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String objKey;
        int objPath;
        String str;
        String[] temp;
        objPath = path.indexOf(urlType) + urlType.length();
        str = path.substring(objPath);
        if(str.contains("?")){
            //正则表达式，"？",需要写成"[?]"
            temp = str.split("[?]");
            objKey = temp[0];
        }else{
            objKey = str;
        }

        return objKey;
    }

    /**
     * 对URL进行格式处理
     * @param path
     * @return
     */
    private final static String formatURL(String path){
        String temp = null;
        if(path.startsWith("http://") || path.startsWith("https://")){
            return path;
        }else{
            try {
                //对URL进行编码处理
                temp = URLEncoder.encode(path, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "http://" + temp;
        }
    }
}
