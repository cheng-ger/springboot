package com.cyl.it.practice.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chengyuanliang
 * @desc 1：在启动类上写@EnableScheduling注解
 *
 *          2：在要定时任务的类上写@component
 *
 *          3：在要定时执行的方法上写@Scheduled(fixedRate=毫秒数)。
 *
 *          1.fixedDelay和fixedRate，单位是毫秒，它们的区别就是：
 *
 *           fixedRate就是每多次分钟一次，不论你业务执行花费了多少时间。我都是按时间执行1次，
 *          而fixedDelay是当任务执行完毕后在执行。所以根据实际业务不同，我们会选择不同的方式。
 *
 *          2.cron表达式：比如你要设置每天什么时候执行，就可以用它
 *
 *          cron表达式，有专门的语法，而且感觉有点绕人，不过简单来说，
 *          大家记住一些常用的用法即可，特殊的语法可以单独去查。
 *          cron一共有7位，但是最后一位是年，可以留空，所以我们可以写6位：
 *          * 第一位，表示秒，取值0-59
 *          * 第二位，表示分，取值0-59
 *          * 第三位，表示小时，取值0-23
 *          * 第四位，日期天/日，取值1-31
 *          * 第五位，日期月份，取值1-12
 *          * 第六位，星期，取值1-7，星期一，星期二...，注：不是第1周，第二周的意思
 *                      另外：1表示星期天，2表示星期一。
 *          * 第7为，年份，可以留空，取值1970-2099
 *
 *
 *          (*)星号：可以理解为每的意思，每秒，每分，每天，每月，每年...
 *          (?)问号：问号只能出现在日期和星期这两个位置。
 *          (-)减号：表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12
 *          (,)逗号：表达一个列表值，如在星期字段中使用“1,2,4”，则表示星期一，星期二，星期四
 *          (/)斜杠：如：x/y，x是开始值，y是步长，比如在第一位（秒） 0/15就是，从0秒开始，每15秒,后就是0，15，30，45，60
 *            另：*\/y，等同于0/y
 *
 *            0 0 3 * * ?     每天3点执行
 *            0 5 3 * * ?     每天3点5分执行
 *            0 5 3 ? * *     每天3点5分执行，与上面作用相同
 *            0 5/10 3 * * ?  每天3点的 5分，15分，25分，35分，45分，55分这几个时间点执行
 *            0 10 3 ? * 1    每周星期天，3点10分 执行，注：1表示星期天
 *            0 10 3 ? * 1#3  每个月的第三个星期，星期天 执行，#号只能出现在星期的位置
 * @since 2019-06-24
 */


@Component
public class ScheduleTask {

    static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //执行完方法后 5秒再执行
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask(){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String format = simpleDateFormat.format(new Date());
        System.out.println("fixedDelay:%s" + format );
    }

    //每隔3秒执行
    @Scheduled(fixedRate = 3000)
    public void fixedRateTask(){
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String format = simpleDateFormat.format(new Date());
        System.out.println("fixedRate:%s" + format );
    }


    //自定时间执行
    @Scheduled(cron = "5/10 * * * * *")
    public void cornTask() {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String format = simpleDateFormat.format(new Date());
        System.out.println("corn:%s" + format);
    }


}
