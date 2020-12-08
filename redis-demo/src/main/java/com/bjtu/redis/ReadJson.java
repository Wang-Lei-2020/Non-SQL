package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 ** Function: 读取JSON文件
 ** Author:   王磊 18301137
 ** Date:     2020年12月5日
 */

public class ReadJson {
    public ReadJson(){

    }

    //读取Json文件，并存到String类型变量中
    public static String readJsonFile(String fileName) {
        //存储文件中的数据
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            //一个字符一个字符的读取文件
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            //关闭资源
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();

            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //得到counter的键值对
    public static Map<String,Object> getCounters(){
        //文件位置
        String path = "src/main/resources/Counters.json";
        String s = readJsonFile(path);
        JSONObject j = JSON.parseObject(s);
        JSONArray ja = j.getJSONArray("counters");
        Map<String,Object> counters = new HashMap<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject key1 = (JSONObject)ja.get(i);
            //System.out.println(key1.toString());

            Counter counter = new Counter(key1);
            //System.out.println(counter.getName());
            //System.out.println(counter.getIndex());
            //System.out.println(counter.getKey());
            //System.out.println(counter.getValue());
            //System.out.println(counter.getFREQ());
            //System.out.println(counter.getStart());
            //System.out.println(counter.getEnd());
            counters.put(counter.getName(),counter);
        }

        //System.out.println(counters.keySet());

        return counters;
    }

    //得到action的键值对
    public static Map<String,Object> getActions(){
        //文件位置
        String path = "src/main/resources/Actions.json";
        String s = readJsonFile(path);
        JSONObject j = JSON.parseObject(s);
        JSONArray ja = j.getJSONArray("actions");
        Map<String,Object> actions = new HashMap<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject key1 = (JSONObject)ja.get(i);
            //System.out.println(key1.toString());

            Action action = new Action(key1);
            //System.out.println(action.getName());
            //System.out.println(action.getSave().toString());
            //System.out.println(action.getRetrieve().toString());

            actions.put(action.getName(),action);
        }

        //System.out.println(actions.keySet());

        return actions;
    }

    //返回所有actions的名字（有顺序！）
    public static ArrayList<String> getANames(){
        ArrayList<String> aNames = new ArrayList<String>();
        String path = "src/main/resources/Actions.json";
        String s = readJsonFile(path);
        JSONObject j = JSON.parseObject(s);
        JSONArray ja = j.getJSONArray("actions");
        for(int i = 0; i<ja.size(); i++){
            JSONObject key1 = (JSONObject)ja.get(i);
            aNames.add(key1.getString("actionName"));
        }
        return aNames;
    }

    /*
    //Test
    public static void main(String[] args) throws Exception {
        ReadJson r = new ReadJson();
        r.getCounters();
        System.out.println("_______________________________________________________________________");
        r.getActions();
    }
    */

}
