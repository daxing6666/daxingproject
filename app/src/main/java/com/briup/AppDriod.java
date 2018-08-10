package com.briup;

import android.app.Application;
import com.briup.frame.logger.AndroidLogAdapter;
import com.briup.frame.logger.FormatStrategy;
import com.briup.frame.logger.Logger;
import com.briup.frame.logger.PrettyFormatStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDriod extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 日志打印初始化
         */
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认2
                .methodOffset(2)        // （可选）隐藏内部方法调用到偏移量。 默认5
                //.logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag("Briup")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        /**
         * 项目上线前，可以实现以下方法，以保证上线后不输出日志。
         */
        /*Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });*/

        //todo
        Logger.d("message");
        Logger.w("no thread info and only 1 method");
        Logger.i("no thread info and method info");
        Logger.e("Custom tag for only one use");//错误信息 红色的
        Logger.json("{ \"key\": 3, \"value\": something}");
        Logger.d(Arrays.asList("foo", "bar"));

        //打印list数据
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        Logger.d(list);
        //打印map数据
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value2");
        Logger.d(map);
        //打印Json数据
        String JSON_CONTENT = "{\"weatherinfo\":{\"city\":\"北京\",\"cityid\":\"101010100\"," +
                "\"temp\":\"18\",\"WD\":\"东南风\",\"WS\":\"1级\",\"SD\":\"17%\",\"WSE\":\"1\"," +
                "\"time\":\"17:05\",\"isRadar\":\"1\",\"Radar\":\"JC_RADAR_AZ9010_JB\"," +
                "\"njd\":\"暂无实况\",\"qy\":\"1011\",\"rain\":\"0\"}}";
        Logger.json(JSON_CONTENT);

        //打印Xml数据
        String XML_CONTENT = "<china dn=\"nay\"><city quName=\"黑龙江\" pyName=\"heilongjiang\" " +
                "cityname=\"哈尔滨\" state1=\"1\" state2=\"1\" stateDetailed=\"多云\"/><city quName=\"吉林\"" +
                " pyName=\"jilin\" " +
                "cityname=\"长春\" state1=\"0\" state2=\"0\" stateDetailed=\"晴\"/><city quName=\"辽宁\" " +
                "pyName=\"liaoning\" " +
                "cityname=\"沈阳\" state1=\"1\" state2=\"0\" stateDetailed=\"多云转晴\"/><city " +
                "quName=\"海南\" pyName=\"hainan\" " +
                "cityname=\"海口\" state1=\"22\" state2=\"21\" stateDetailed=\"中到大雨转小到中雨\"/></china>";
        Logger.xml(XML_CONTENT);
    }
}
