package com.linkcreators.listviewjson.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jun on 2015/12/2.
 */
public class HttpUtils {

    public HttpUtils() {

    }

    public static String getJsonContent(String path){
        try{
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if(200 == code){
                return changeInputStream(connection.getInputStream());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     * @param inputStream
     * @return
     */
    private static String changeInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try{
            while ((len = inputStream.read(data)) != -1){
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
