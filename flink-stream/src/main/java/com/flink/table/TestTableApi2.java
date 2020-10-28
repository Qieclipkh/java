package com.flink.table;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.GroupedTable;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;

import com.flink.entity.StationLog;

public class TestTableApi2 {
    public static void main(String[] args) throws Exception {
        /**
         * Flink Stream Query
         */
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
        //读取数据
        DataStream<String> source = fsEnv.socketTextStream("172.16.32.38", 1234);
        //3、转换和处理数据
        DataStream<StationLog> map = source
                .map(new MapFunction<String, StationLog>() {
                    @Override
                    public StationLog map(String value) throws Exception {
                        String[] arr = value.split(",");
                        return new StationLog(arr[0], arr[1], arr[2], arr[3], Long.parseLong(arr[4]), Integer.parseInt(arr[5]));
                    }
                });
        //创建表
        Table table = fsTableEnv.fromDataStream(map);
        table.printSchema();
      /*  Table result = table.filter("call_type == 'success'").select("sid");
        DataStream<Row> f = fsTableEnv.toAppendStream(result, Row.class);
        f.print();*/


        GroupedTable sid = table.groupBy("sid");
        Table select = sid.select("sid,sid.count");
        DataStream<Tuple2<Boolean, Row>> b = fsTableEnv.toRetractStream(select, Row.class);
        /**
         * 输出结果，false 是旧数据，使用filter过滤
         * (true,station_9,1)
         * (false,station_9,1)
         * (true,station_9,2)
         */
        b.print();

        fsEnv.execute("s");
    }
}
