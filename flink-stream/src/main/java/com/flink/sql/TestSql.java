package com.flink.sql;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.FileSystem;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.OldCsv;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;

import com.flink.table.CSVTableApi1;

public class TestSql {
    public static void main(String[] args) throws Exception {
        /**
         * Flink Stream Query
         */
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
        //读取数据
        String path = CSVTableApi1.class.getResource("/station.log").getPath();
        //创建表
        Schema schema = new Schema()
                .field("sid", DataTypes.STRING())
                .field("call_in", DataTypes.STRING())
                .field("call_out", DataTypes.STRING())
                .field("call_type", DataTypes.STRING())
                .field("call_time", DataTypes.DOUBLE())
                .field("duration", DataTypes.INT())
                ;
        new Kafka().version("").startFromEarliest();
        fsTableEnv.connect(new FileSystem().path(path)).withFormat(new Csv().fieldDelimiter(',')).withSchema(schema).createTemporaryTable("t_stationlog");
        Table table = fsTableEnv.sqlQuery("select sid,sum(duration) from t_stationlog group by sid");
        //HOP  SESSION TUMBLE_START TUMBLE_END
        fsTableEnv.sqlQuery("select sid,sum(duration) from t_stationlog group by TUMBLE(call_time,interval '5' seconds),sid");
        DataStream<Tuple2<Boolean, Row>> tuple2DataStream = fsTableEnv.toRetractStream(table, Row.class);
        tuple2DataStream.print();
        fsTableEnv.execute("d");
    }
}
