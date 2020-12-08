package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

/*
 ** Function: Action实体类
 ** Author:   王磊 18301137
 ** Date:     2020年12月5日
 */

public class Action {
    private final String ActionName;  //名字
    private final ArrayList<String> save;  //要保存的counters
    private final ArrayList<String> retrieve;  //要检索的counters

    public Action(JSONObject j) throws JSONException {
        this.ActionName = j.getString("actionName");
        save = new ArrayList<String>();
        JSONArray a;
        //得到要save的countes列表
        if(j.containsKey("save")) {
            a = j.getJSONArray("save");
            for (int i = 0; i < a.size(); i++) {
                save.add(a.getString(i));
            }
        }

        //得到要retrieve的counters列表
        retrieve = new ArrayList<String>();
        a = j.getJSONArray("retrieve");
        for(int i = 0;i<a.size();i++){
            retrieve.add(a.getString(i));
        }
    }

    public String getName(){
        return this.ActionName;
    }

    public ArrayList<String> getSave(){
        return this.save;
    }

    public ArrayList<String> getRetrieve(){
        return this.retrieve;
    }
}
