package com.linkcreators.listviewjson.bean;

/**
 * Created by Jun on 2015/11/25.
 */
public class JsonBean {

    /**
     * PublishTime : 2015-11-25T10:19:12.1870000+08:00
     * Title : 长安CS35增1.5T车型
     * ID : 6568511
     * FirstPicUrl : http://img1.bitautoimg.com/wapimg-66-44/bitauto/news/2015/11/20151125101234422_320.jpg
     */

    private String PublishTime;
    private String Title;
    private int ID;
    private String FirstPicUrl;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstPicUrl() {
        return FirstPicUrl;
    }

    public void setFirstPicUrl(String firstPicUrl) {
        FirstPicUrl = firstPicUrl;
    }
}
