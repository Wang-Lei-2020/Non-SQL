package com.bjtu.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/*
 ** Function: 测试每个counter功能，并且可以监听json文件改变
 ** Author:   王磊 18301137
 ** Date:     2020年12月6日
 */

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */
@SpringBootApplication
public class RedisDemoApplication {

    private static Map<String, Object> counters = ReadJson.getCounters();
    private static Map<String, Object> actions = ReadJson.getActions();

    //更改json文件时，重新读取counters和actions
    public static void setCounters(){
        counters = ReadJson.getCounters();
        actions = ReadJson.getActions();
    }

    public static void main(String[] args) throws InterruptedException {
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
        Scanner input = new Scanner(System.in);

        //实现文件监听功能
        FileListenerTest.monitoring();

        System.out.println("—————————————————————————————————————————————");
        System.out.println("—————————— 欢迎使用Counter计数器系统! -——————————");
        System.out.println("——————————— Author: 18301137 王磊 ————————————");
        System.out.println("—————————————————————————————————————————————");

        //操作循环
        while (true) {
            System.out.println("0)退出Counters系统。");
            System.out.println("1)展示所有的Counter。");
            System.out.println("2)展示所有的Action。");
            System.out.println("3)执行Action。");
            System.out.print("请输入选择>");
            try {
                choose = input.nextInt();
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
                    choose = input.nextInt();
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

/*

总结：

1、获取运行环境信息和回调接口。例如ApplicationContextIntializer、ApplicationListener。
完成后，通知所有SpringApplicationRunListener执行started()。

2、创建并准备Environment。
完成后，通知所有SpringApplicationRunListener执行environmentPrepared()

3、创建并初始化 ApplicationContext 。例如，设置 Environment、加载配置等
完成后，通知所有SpringApplicationRunListener执行contextPrepared()、contextLoaded()

4、执行 ApplicationContext 的 refresh，完成程序启动
完成后，遍历执行 CommanadLineRunner、通知SpringApplicationRunListener 执行 finished()

参考：
https://blog.csdn.net/zxzzxzzxz123/article/details/69941910
https://www.cnblogs.com/shamo89/p/8184960.html
https://www.cnblogs.com/trgl/p/7353782.html

分析：

1） 创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法

public static ConfigurableApplicationContext run(Object source, String... args)

public static ConfigurableApplicationContext run(Object[] sources, String[] args)

2） SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，
方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的
SpringApplicationRunListener，调用它们的started()方法。


public SpringApplication(Object... sources)

private final Set<Object> sources = new LinkedHashSet<Object>();

private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

...

private void initialize(Object[] sources)

3） 创建并配置当前SpringBoot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。

private boolean deduceWebEnvironment()

4） 遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法，通知Environment准备完毕。

5） 如果SpringApplication的showBanner属性被设置为true，则打印banner。

6） 根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，
决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，
然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，
决定是否使用自定义的ResourceLoader，当然，最重要的，
将之前准备好的Environment设置给创建好的ApplicationContext使用。

7） ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，
查找并加载classpath中所有可用的ApplicationContext-Initializer，
然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法
来对已经创建好的ApplicationContext进行进一步的处理。

8） 遍历调用所有SpringApplicationRunListener的contextPrepared()方法。

9） 最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的
IoC容器配置加载到已经准备完毕的ApplicationContext。

10） 遍历调用所有SpringApplicationRunListener的contextLoaded()方法。

11） 调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。

12） 查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。

13） 正常情况下，遍历执行SpringApplicationRunListener的finished()方法、
（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，
只不过这种情况下会将异常信息一并传入处理）


private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type)

private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args)

public void setInitializers

private Class<?> deduceMainApplicationClass()

public ConfigurableApplicationContext run(String... args)

private void configureHeadlessProperty()

private SpringApplicationRunListeners getRunListeners(String[] args)

public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader)


*/
