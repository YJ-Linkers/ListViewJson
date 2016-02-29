package com.linkcreators.listviewjson.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jun on 2015/12/2.
 */
public class JSONTools {

    public JSONTools() {

    }

    /**
     * 获取对象数据
     * @param key
     * @param jsonString
     * @return
     */
    public static JsonBean getJsonBean(String key, String jsonString){
        JsonBean jsonBean = new JsonBean();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject newsObject = jsonObject.getJSONObject(key);
            jsonBean.setID(newsObject.getInt("ID"));
            jsonBean.setTitle(newsObject.getString("Title"));
            jsonBean.setPublishTime(newsObject.getString("PublishTime"));
            jsonBean.setFirstPicUrl(newsObject.getString("FirstPicUrl"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        return jsonBean;
    }

    /**
     * 获取对象数组数据
     * @param key
     * @param jsonString
     * @return
     */
    public static List<JsonBean> getJsonBeanObject(String key, String jsonString){
        List<JsonBean> jsonBeanList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            //返回json数组
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                JsonBean jsonBean = new JsonBean();
                jsonBean.setID(obj.getInt("ID"));
                jsonBean.setTitle(obj.getString("Title"));
                jsonBean.setPublishTime(obj.getString("PublishTime"));
                jsonBean.setFirstPicUrl(obj.getString("FirstPicUrl"));
                jsonBeanList.add(jsonBean);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return jsonBeanList;
    }

    /**
     * 获取数组对象数据
     */
    public static List<JsonBean> getJsonObject(String jsonString) throws JSONException {

        JSONArray jsonArray = new JSONArray(jsonString);
        List<JsonBean> jsonBeanList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            JsonBean jsonBean = new JsonBean();
            jsonBean.setID(object.getInt("ID"));
            jsonBean.setTitle(object.getString("Title"));
            jsonBean.setPublishTime(object.getString("PublishTime"));
            jsonBean.setFirstPicUrl(object.getString("FirstPicUrl"));
            jsonBeanList.add(jsonBean);
        }

        return jsonBeanList;
    }
    /**
     * 获取String数组数据
     * @param key
     * @param jsonString
     * @return
     */

    public static List<String> getList(String key, String jsonString){
        List<String> stringList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for(int i = 0; i < jsonArray.length(); i++){
                String msg = jsonArray.getString(i);
                stringList.add(msg);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return stringList;
    }

    /**
     * 获取对象的Map集合数据
     * @param key
     * @param jsonString
     * @return
     */
    public static List<HashMap<String, Object>> getListMap(String key, String jsonString){
        List<HashMap<String, Object>> mapList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                HashMap<String, Object> objectMap = new HashMap<>();
                Iterator<String> iterator = obj.keys();
                while (iterator.hasNext()){
                    String jsonKey = iterator.next();
                    Object jsonValue = obj.get(jsonKey);
                    if(null == jsonValue){
                        jsonValue = "";
                    }
                    objectMap.put(jsonKey, jsonValue);
                }
                mapList.add(objectMap);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return mapList;
    }


    /**
     * 解析
     *
     * @throws JSONException
     */
    public static ArrayList<HashMap<String, Object>> getData(String jsonStr)
            throws JSONException {
        /******************* 解析 ***********************/
        JSONArray jsonArray = new JSONArray(jsonStr);
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<>();
            map.put("ID", jsonObject.getInt("ID"));
            map.put("Title", jsonObject.getString("Title"));
            map.put("PublishTime", jsonObject.getString("PublishTime"));
            map.put("FirstPicUrl", jsonObject.getString("FirstPicUrl"));
            arrayList.add(map);
        }
        return arrayList;
    }
}
