package com.flink.table;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;

public class CSVTableApi1 {
    public static void main(String[] args) throws Exception {
        /**
         * Flink Stream Query
         */
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
        //读取数据
        String path = CSVTableApi1.class.getResource("/station.log").getPath();
        CsvTableSource csvTableSource = CsvTableSource.builder()
                .path(path)
                .field("sid", DataTypes.STRING())
                .field("call_in", DataTypes.STRING())
                .field("call_out", DataTypes.STRING())
                .field("call_type", DataTypes.STRING())
                .field("call_time", DataTypes.DOUBLE())
                .field("duration", DataTypes.INT())
                .fieldDelimiter(",")
                .lineDelimiter("\r\n")
                .emptyColumnAsNull()
                .build();
        //创建表
        Table table = fsTableEnv.fromTableSource(csvTableSource);
        table.printSchema();
        Table result = table.filter("call_type == 'success'").select("sid");

        DataStream<Row> f = fsTableEnv.toAppendStream(result, Row.class);
        f.print();
        fsEnv.execute("s");
    }
}
