package com.bjtu.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 ** Function: 实现每个counter的具体功能，仅向外暴露一个count函数接口
 ** Author:   王磊 18301137
 ** Date:     2020年12月6日
 */

enum counterID{
    incr,incrBy,decr,decrBy,showUserNum,showUserInFREQ,showUserOutFREQ,showUserInOutFREQ
}

public class CounterFunc {
    RedisOperation ro = new RedisOperation();

    public CounterFunc(){ }

    //根据counter的名字选择运行以下哪一个函数,仅暴露这一个函数接口
    public void count(Counter counter){
        String name = counter.getName();
        System.out.println(counterID.valueOf(name));
        switch(counterID.valueOf(name)){
            case incr:
                incr(counter);
                break;
            case incrBy:
                incrBy(counter);
                break;
            case decr:
                decr(counter);
                break;
            case decrBy:
                decrBy(counter);
                break;
            case showUserNum:
                showUserNum(counter);
                break;
            case showUserInFREQ:
                showUserInFREQ(counter);
                break;
            case showUserOutFREQ:
                showUserOutFREQ(counter);
                break;
            case showUserInOutFREQ:
                showUserInOutFREQ(counter);
                break;
            default:
                break;
        }
    }

    //user增加1,并往userInList中添加当前时间
    private boolean incr(Counter counter){
        //获取user和userInList在Redis数据库中的key值
        String user = counter.getKey().get(0);
        String userInList = counter.getKey().get(1);

        //若没有创建user或者已过期，则要设置
        if(ro.ttl(user)==-2){
            ro.set(user,"1");
        }else{
            ro.incr(user);
        }
        //将现在的时间变为字符串以及yyyyMMddHHmm格式
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String sDate=f.format(date);
        ro.lpush(userInList,sDate);
        System.out.println("有用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"进入了直播间。");
        ro.closeJedis();
        return true;

    }

    //user增加任意值,并往userInList中添加当前时间
    private boolean incrBy(Counter counter){
        //获取user和userInList在Redis数据库中的key值
        String user = counter.getKey().get(0);
        String userInList = counter.getKey().get(1);

        long value = Long.parseLong(counter.getValue());
        //若没有创建user或者已过期，则要设置
        if(ro.ttl(user)==-2){
            ro.set(user,String.valueOf(value));
        }else{
            ro.incrBy(user,value);
        }

        //将现在的时间变为字符串以及yyyyMMddHHmm格式
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String sDate=f.format(date);
        //存入value个user到列表中
        for(int i = 0; i<value; i++){
            ro.lpush(userInList,sDate);
            System.out.println("有用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                    +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"进入了直播间。");
        }
        ro.closeJedis();
        return true;
    }

    //user减少1,并往userOutList中添加当前时间
    private boolean decr(Counter counter){
        //获取user和userOutList在Redis数据库中的key值
        String user = counter.getKey().get(0);
        String userOutList = counter.getKey().get(1);

        //若没有创建user或者已过期，则提示信息
        if(ro.ttl(user)==-2){
            System.out.println("当前无user数据，不能减少用户数量！");
            return false;
        }else if(Long.parseLong(ro.get(user)) == 0){//用户数量已经为0
            System.out.println("user数量不够，不支持减少1个用户");
            return false;
        }
        else {
            ro.decr(user);
            //将现在的时间变为字符串以及yyyyMMddHHmm格式
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String sDate=f.format(date);
            ro.lpush(userOutList,sDate);
            System.out.println("有用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                    +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"离开了直播间。");
        }

        ro.closeJedis();
        return true;
    }

    //user减少任意值,并往userOutList中添加当前时间
    private boolean decrBy(Counter counter){
        //获取user和userOutList在Redis数据库中的key值
        String user = counter.getKey().get(0);
        String userOutList = counter.getKey().get(1);
        long value = Long.parseLong(counter.getValue());

        //若没有创建user或者已过期，则提示信息
        if(ro.ttl(user)==-2){
            System.out.println("当前无user数据，不能减少用户数量！");
            return false;
        }else if(Long.parseLong(ro.get(user))<value){
            System.out.println("user数量不够，不支持减少这些数量的用户");
            return false;
        }else{
            ro.decrBy(user,value);
            //将现在的时间变为字符串以及yyyyMMddHHmm格式
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = new Date();
            String sDate=f.format(date);
            for(int i = 0; i<value; i++) {
                ro.lpush(userOutList, sDate);
                System.out.println("有用户在"+sDate.substring(0,4)+"年"+sDate.substring(4,6)+"月"+sDate.substring(6,8)
                        +"日"+sDate.substring(8,10)+":"+sDate.substring(10,12)+"离开了直播间。");
            }
        }
        ro.closeJedis();
        return true;
    }

    //展示现在的user数量
    private long showUserNum(Counter counter){
        String num;
        String user = counter.getKey().get(0);

        if(ro.ttl(user) == -2){
            System.out.println("当前无user数据，不支持查看user数量！");
            return -1;
        }else{
            num = ro.get(user);

            SimpleDateFormat f = new SimpleDateFormat("yyyy年-MM月dd日-HH:mm");
            Date date = new Date();
            String sDate=f.format(date);

            System.out.println("当前时间:"+sDate+",直播间共有 "+num+" 名用户。");
        }
        ro.closeJedis();
        return Long.parseLong(num);
    }

    //按周期显示用户进入直播间的时间与总进入用户量
    private long showUserInFREQ(Counter counter){
        String userInList = counter.getKey().get(0);
        String start = counter.getStart();
        String end = counter.getEnd();
        long sum = 0;

        if(start==null||end==null){
            System.out.println("没有开始与结束时间，错误的计数器！");
            ro.closeJedis();
            return -1 ;
        }

        if(start.compareTo(end)>0){
            System.out.println("开始时间晚于结束时间，错误的计数器！");
            ro.closeJedis();
            return -1 ;
        }

        if(ro.ttl(userInList)==-2){
            System.out.println("不存在userInList列表，无法显示用户进入周期计数！");
            return -1 ;
        }else {
            //userInList列表中的时间数据
            long i = 0;
            String userInTime = ro.lindex(userInList, i);
            while(userInTime.compareTo(start)>=0 && userInTime.compareTo(end)<=0){
                System.out.println("有用户在"+userInTime.substring(0,4)+"年"+userInTime.substring(4,6)+"月"+userInTime.substring(6,8)
                                   +"日"+userInTime.substring(8,10)+":"+userInTime.substring(10,12)+"进入了直播间。");
                sum++;
                //取下一个用户进入的时间
                i++;
                //判断下标是否超过了列表总数（否则导致越界）
                if(i<ro.getLen(userInList)) {
                    userInTime = ro.lindex(userInList, i);
                }else{
                    break;
                }
            }
            System.out.println(start.substring(0,4)+"年"+start.substring(4,6)+"月"+start.substring(6,8)+"日"+start.substring(8,10)+":"+start.substring(10,12)+"——"
            +end.substring(0,4)+"年"+end.substring(4,6)+"月"+end.substring(6,8)+"日"+end.substring(8,10)+":"+end.substring(10,12)+" 时间段内共有 "+sum+" 名用户进入直播间。");
        }

        ro.closeJedis();
        return sum;
    }

    //按周期显示用户离开直播间的时间与总离开用户量
    private long showUserOutFREQ(Counter counter){
        String userOutList = counter.getKey().get(0);
        String start = counter.getStart();
        String end = counter.getEnd();
        long sum = 0;

        if(start==null||end==null){
            System.out.println("没有开始与结束时间，错误的计数器！");
            ro.closeJedis();
            return -1;
        }
        if(start.compareTo(end)>0){
            System.out.println("开始时间晚于结束时间，错误的计数器！");
            ro.closeJedis();
            return -1;
        }

        if(ro.ttl(userOutList)==-2){
            System.out.println("不存在userOutList列表，无法显示用户进入周期计数！");
            return -1;
        }else {
            //userOutList列表中的时间数据
            long i = 0;
            String userOutTime = ro.lindex(userOutList, i);
            while(userOutTime.compareTo(start)>=0 && userOutTime.compareTo(end)<=0){
                System.out.println("有用户在"+userOutTime.substring(0,4)+"年"+userOutTime.substring(4,6)+"月"+userOutTime.substring(6,8)
                        +"日"+userOutTime.substring(8,10)+":"+userOutTime.substring(10,12)+"离开了直播间。");
                sum++;
                //取下一个用户进入的时间
                i++;
                //判断下标是否超过了列表总数（否则导致越界）
                if(i<ro.getLen(userOutList)) {
                    userOutTime = ro.lindex(userOutList, i);
                }else{
                    break;
                }
            }
            System.out.println(start.substring(0,4)+"年"+start.substring(4,6)+"月"+start.substring(6,8)+"日"+start.substring(8,10)+":"+start.substring(10,12)+"——"
                    +end.substring(0,4)+"年"+end.substring(4,6)+"月"+end.substring(6,8)+"日"+end.substring(8,10)+":"+end.substring(10,12)+" 时间段内共有 "+sum+" 名用户离开直播间。");
        }

        ro.closeJedis();
        return sum;
    }

    //**按周期显示用户进入离开直播间的实时数据**
    private boolean showUserInOutFREQ(Counter counter){
        String userInList = counter.getKey().get(0);
        String userOutList = counter.getKey().get(1);
        //FREQ的开始与结束时间
        String start = counter.getStart();
        String end = counter.getEnd();

        if(ro.ttl(userInList)==-2){
            System.out.println("没有userInList列表数据，无法查看用户进入数据！");
            ro.closeJedis();
            return false;
        }else if(ro.ttl(userOutList)==-2){
            System.out.println("没有userOutList列表数据，无法查看用户离开数据！");
            ro.closeJedis();
            return false;
        } else if(start==null||end==null){
            System.out.println("没有周期的开始与结束时间，错误的计数器！");
            ro.closeJedis();
            return false;
        } else if(start.compareTo(end)>0){
            System.out.println("开始时间晚于结束时间，错误的计数器！");
            ro.closeJedis();
            return false;
        }else{
            //用来表示两个列表的下标
            long in=0;
            long out=0;
            //分别统计在时间段内进入和离开直播间的人数
            int inSum = 0;
            int outSum = 0;
            //存放两个列表中的时间数据
            String inTime = ro.lindex(userInList,in);
            String outTime = ro.lindex(userOutList,out);
            //将In列表中数据落在FREQ范围内
            while(!(inTime.compareTo(start)>=0&&inTime.compareTo(end)<=0)){
                in++;
                //判断下标是否超过了列表总数（否则导致越界）
                if(in<ro.getLen(userInList)) {
                    inTime = ro.lindex(userInList, in);
                }else{
                    break;
                }
            }
            //将Out列表中数据落在FREQ范围内
            while(!(outTime.compareTo(start)>=0&&outTime.compareTo(end)<=0)){
                out++;
                //判断下标是否超过了列表总数（否则导致越界）
                if(out<ro.getLen(userOutList)) {
                    outTime = ro.lindex(userOutList, out);
                }else{
                    break;
                }
            }
            //循环取两个列表中的值并比较时间早晚，时间晚的先进行操作
            while(true){
                //取时间结束，判断条件为时间早于start和取完列表两个条件进行笛卡尔积
                if((inTime.compareTo(start)<0&&outTime.compareTo(start)<0)||(((in>=ro.getLen(userInList))&&(out>=ro.getLen(userOutList))))
                ||(inTime.compareTo(start)<0&&out>=ro.getLen(userOutList))||(outTime.compareTo(start)<0&&in>=ro.getLen(userInList))){
                    //打印总的用户进出量
                    System.out.println(start.substring(0,4)+"年"+start.substring(4,6)+"月"+start.substring(6,8)+"日"+start.substring(8,10)+":"+start.substring(10,12)+"——"
                            +end.substring(0,4)+"年"+end.substring(4,6)+"月"+end.substring(6,8)+"日"+end.substring(8,10)+":"+end.substring(10,12)+" 时间段内共有 "+inSum+" 名用户进入直播间。");
                    System.out.println(start.substring(0,4)+"年"+start.substring(4,6)+"月"+start.substring(6,8)+"日"+start.substring(8,10)+":"+start.substring(10,12)+"——"
                            +end.substring(0,4)+"年"+end.substring(4,6)+"月"+end.substring(6,8)+"日"+end.substring(8,10)+":"+end.substring(10,12)+" 时间段内共有 "+outSum+" 名用户离开直播间。");
                    //跳出循环
                    break;
                }

                //System.out.println(in+"  "+out);
                //in时间晚，或者out列表已经取完，并且in列表没有取完
                if((inTime.compareTo(outTime)>=0||out>=ro.getLen(userOutList))&&in<ro.getLen(userInList)){
                    System.out.println("有用户在"+inTime.substring(0,4)+"年"+inTime.substring(4,6)+"月"+inTime.substring(6,8)+"日"+
                            inTime.substring(8,10)+":"+inTime.substring(10,12)+"进入了直播间。");
                    //in的数量+1
                    inSum++;
                    //取in列表下一个时间
                    in++;
                    //避免下标越界
                    if(in<ro.getLen(userInList)) {
                        inTime = ro.lindex(userInList, in);
                    }
                    //如果out列表没有取完
                }else if(!(out>=ro.getLen(userOutList))){
                    System.out.println("有用户在"+outTime.substring(0,4)+"年"+outTime.substring(4,6)+"月"+outTime.substring(6,8)+"日"+
                            outTime.substring(8,10)+":"+outTime.substring(10,12)+"离开了直播间。");
                    //out的数量+1
                    outSum++;
                    //取out列表的下一个时间
                    out++;
                    //避免下标越界
                    if(out<ro.getLen(userOutList)) {
                        outTime = ro.lindex(userOutList, out);
                    }
                }
            }

        }
        //关闭jedis连接
        ro.closeJedis();
        return true;
    }

    /*
    //测试成功
    public static void main(String[] args){
        CounterFunc c = new CounterFunc();
        Map<String,Object> counters = ReadJson.getCounters();
        //Counter c1 = (Counter)counters.get("incr");
        // c.incr(c1);
        //Counter c2 = (Counter)counters.get("incrBy");
        //c.incrBy(c2);
        //Counter c3 = (Counter)counters.get("decr");
        //c.decr(c3);
        //Counter c4 = (Counter)counters.get("decrBy");
        //c.decrBy(c4);
        //Counter c5 = (Counter)counters.get("showUserNum");
        //c.showUserNum(c5);
        //Counter c6 = (Counter)counters.get("showUserInFREQ");
        //c.showUserInFREQ(c6);
        //Counter c7 = (Counter)counters.get("showUserOutFREQ");
        //c.showUserOutFREQ(c7);
        //Counter c8 = (Counter)counters.get("showUserInOutFREQ");
        //c.showUserInOutFREQ(c8);
        Counter c9 = (Counter)counters.get("showUserInOutFREQ");
        c.count(c9);
    }
    */
}
