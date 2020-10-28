package com.flink.table;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.GroupWindow;
import org.apache.flink.table.api.Slide;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

import com.flink.entity.StationLog;

import static org.apache.flink.table.api.DataTypes.*;

public class TestUDF {
    public static void main(String[] args) throws Exception {
        /**
         * Flink Stream Query
         */
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
        fsEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //读取数据
        DataStream<String> dataStream = fsEnv.socketTextStream("172.16.32.38", 10010);
        //3、转换和处理数据
        DataStream<StationLog> map = dataStream
                .map(new MapFunction<String, StationLog>() {
                    @Override
                    public StationLog map(String value) throws Exception {
                        String[] arr = value.split(",");
                        return new StationLog(arr[0], arr[1], arr[2], arr[3], Long.parseLong(arr[4]), Integer.parseInt(arr[5]));
                    }
                });
        Table table = fsTableEnv.fromDataStream(map,"sid,call_time.rowtime");
        table.window(Tumble.over("5.second").on("callTime").as("window"))
                .groupBy("window,sid")//必須使用窗口字段作为分组字段
                .select("sid,window.start,window.end,sid.count");//获取窗口的开始和结束时间
        Slide.over("5.second").every("2.second").on("").as("window");
        // 自定义函数，切割数据
        fsTableEnv.execute("");
    }

    class MyFlatMapFunction extends TableFunction<Row> {

        /**
         * 定义函数处理之后的返回类型
         *
         * @return
         */
        @Override
        public TypeInformation<Row> getResultType() {
            Types.ROW(Types.STRING, Types.INT);
            return super.getResultType();
        }
    }
}
