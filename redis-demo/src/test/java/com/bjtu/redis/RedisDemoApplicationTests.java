package com.bjtu.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;

/*
 ** Function: 对整个项目所有counter以及action的操作遍历测试
 ** Tips: 若想人机交互测试，请运行src/main/java/com.bjtu.redis/RedisDemoApplication文件（可以测试文件监听功能）！！！
 ** Author:   王磊 18301137
 ** Date:     2020年12月5日
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

    private static Map<String, Object> counters = ReadJson.getCounters();
    private static Map<String, Object> actions = ReadJson.getActions();

    //更改json文件时，重新读取counters和actions
    public void setCounters(){
        counters = ReadJson.getCounters();
        actions = ReadJson.getActions();
    }

    @Test
    public void test() {
        //得到counters和actions
        counters = ReadJson.getCounters();
        actions = ReadJson.getActions();
        //SpringApplication.run(RedisDemoApplication.class, args);
        //实现计数器功能
        CounterFunc c = new CounterFunc();

        //存放action的名字（有序）
        ArrayList<String> aNames;
        aNames = ReadJson.getANames();
        //用户的选择
        int choose = 0;
        //输入
        //Scanner input = new Scanner(System.in);

        //实现文件监听功能
        FileListenerTest.monitoring();

        System.out.println("—————————————————————————————————————————————");
        System.out.println("—————————— 欢迎使用Counter计数器系统! -——————————");
        System.out.println("——————————— Author: 18301137 王磊 ————————————");
        System.out.println("—————————————————————————————————————————————");

        //每次用户的选择，这些可以整个遍历所有操作（除去文件监听功能）
        int [] test = {1,2,3,1,2,3,4,5,6,7,8,0,0};
        int x = -1;
        //操作循环
        while (true) {
            x++;
            System.out.println("0)退出Counters系统。");
            System.out.println("1)展示所有的Counter。");
            System.out.println("2)展示所有的Action。");
            System.out.println("3)执行Action。");
            System.out.print("请输入选择>");
            try {
                choose = test[x];
                //打印选择
                System.out.println(choose);
            } catch (InputMismatchException e) {
                System.out.println("必须输入整数！");
            }

            if (choose == 0) {//退出
                break;
            } else if (choose == 1) {//显示所有计数器
                System.out.println("————————————————— Counters ————————————————————");
                //计数器ID
                int i = 0;
                System.out.println("ID  name");
                //遍历并打印计数器
                for (Map.Entry<String, Object> entry : counters.entrySet()) {
                    i++;
                    System.out.println(i+" : "+entry.getKey());
                }
                System.out.println("—————————————————————————————————————————————");
            } else if (choose == 2) {//显示所有action
                System.out.println("————————————————— Actions ————————————————————");
                //actionID
                int i = 0;
                System.out.println("ID  name");
                //遍历并打印计数器
                for (;i<aNames.size();i++) {
                    System.out.println((i+1)+" : "+aNames.get(i));
                }
                System.out.println("—————————————————————————————————————————————");
            } else if (choose == 3) {
                //进入操作的循环
                while(true){
                    System.out.println("————————————————— Actions ————————————————————");
                    //actionID
                    int i = 0;
                    System.out.println("ID  name");
                    System.out.println("0 : 返回上一级");
                    //遍历并打印计数器
                    for (;i<aNames.size();i++) {
                        System.out.println((i+1)+" : "+aNames.get(i));
                    }
                    System.out.print("请输入一个action的ID>");
                    x++;
                    choose = test[x];
                    //打印选择
                    System.out.println(choose);
                    if(choose == 0){
                        System.out.println("—————————————————————————————————————————————");
                        break;//返回上一级
                    }
                    switch(choose){
                        case 1://用户增加1
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(0))).getSave().get(0)));
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(0))).getRetrieve().get(0)));
                            break;
                        case 2://用户增加任意值
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(1))).getSave().get(0)));
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(1))).getRetrieve().get(0)));
                            break;
                        case 3://用户减少1
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(2))).getSave().get(0)));
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(2))).getRetrieve().get(0)));
                            break;
                        case 4://用户减少任意值
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(3))).getSave().get(0)));
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(3))).getRetrieve().get(0)));
                            break;
                        case 5://展示用户数目
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(4))).getRetrieve().get(0)));
                            break;
                        case 6://展示一段时间内用户进入直播间的数量
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(5))).getRetrieve().get(0)));
                            break;
                        case 7://展示一段时间内用户离开直播间的数量
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(6))).getRetrieve().get(0)));
                            break;
                        case 8://按周期显示用户进入离开直播间的实时数据
                            c.count((Counter)counters.get(((Action)actions.get(aNames.get(7))).getRetrieve().get(0)));
                            break;
                        default://输入错误
                            System.out.println("输入错误，请输入整数0-8");
                            break;

                    }
                    System.out.println("—————————————————————————————————————————————");
                }
            } else {
                System.out.println("必须输入整数0-3！");
            }
        }

        System.out.println("—————————————————————————————————————————————");
        System.out.println("————————— 即将退出Counter计数器系统... ——————————");
        System.out.println("—————————————— 期待您的下次使用！ ———————————————");
        System.out.println("—————————————————————————————————————————————");
    }
}
