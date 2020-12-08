package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

/*
 ** Function: Counter实体类
 ** Author:   王磊 18301137
 ** Date:     2020年12月5日
 */

public class Counter {
    private String counterName; //名字
    private String counterIndex; //索引
    private String type;  //存储类型
    private ArrayList<String> keyField;  //统计key
    private String valueField = "";  //value字段
    private String FREQ;  //对不同周期进行统计
    private String start;  //周期开始的时间
    private String end;  //周期结束的时间
    private int maxSize = -1;  //最大存储个数(-1表示没有限制)
    private long expireTime = -1;  //过期时间(-1表示没有过期时间)

    //构造器，将JSON对象的数据赋给实体类
    public Counter(JSONObject j) throws JSONException {
        counterName = j.getString("counterName");
        counterIndex = j.getString("counterIndex");
        type = j.getString("type");

        //得到keyField的数组值
        JSONArray a = j.getJSONArray("keyField");
        keyField = new ArrayList<String>();
        for(int i = 0;i<a.size();i++){
            //将JSONArray中的所有数据添加到keyField中
            keyField.add(a.getString(i));
        }

        //若有valueField数据，则设置
        if(j.containsKey("valueField")){
            valueField = j.getString("valueField");
        }

        //若有maxSize数据，则设置
        if(j.containsKey("maxSize")){
            maxSize = j.getInteger("maxSize");
        }

        //若有expireTime数据，则设置
        if(j.containsKey("expireTime")){
            expireTime = j.getLong("expireTime");
        }

        //若有FREQ数据，则设置
        if(j.containsKey("FREQ")){
            FREQ = j.getString("FREQ");
            start = FREQ.substring(0,12);
            end = FREQ.substring(13,25);
        }
    }

    public String getName(){
        return this.counterName;
    }

    public String getIndex(){
        return this.counterIndex;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<String> getKey(){
        return this.keyField;
    }

    public String getValue(){
        return this.valueField;
    }

    public String getFREQ(){
        return this.FREQ;
    }

    public String getStart(){
        return this.start;
    }

    public String getEnd(){
        return this.end;
    }

    public int getSize(){
        return this.maxSize;
    }

    public long getExpire(){
        return this.expireTime;
    }
}
