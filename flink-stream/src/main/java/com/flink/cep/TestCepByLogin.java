package com.flink.cep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class TestCepByLogin {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<LoginEvent> dataStream = env.fromCollection(buildLoginEnents()).assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<LoginEvent>(Time.seconds(0)) {
            @Override
            public long extractTimestamp(LoginEvent element) {
                return element.getEventTime() * 1000;
            }
        });
        Pattern<LoginEvent, LoginEvent> pattern = Pattern.<LoginEvent>begin("start").where(new FailCondition())
                .next("faile2").where(new FailCondition())
                .next("faile3").where(new FailCondition())
                .within(Time.seconds(10));
        PatternStream<LoginEvent> pattern1 = CEP.pattern(dataStream.keyBy("username"), pattern);
        SingleOutputStreamOperator<String> select = pattern1.select(new PatternSelectFunction<LoginEvent, String>() {
            @Override
            public String select(Map<String, List<LoginEvent>> map) throws Exception {
                StringBuilder sb = new StringBuilder();
                map.forEach((k, v) -> {
                    v.forEach(loginEvent -> {
                        String username = loginEvent.getUsername();
                        sb.append(username).append(":").append(loginEvent.getEventTime());
                    });
                });
                return sb.toString();
            }
        });
        select.print();
        env.execute("TestCepByLogin");
    }


    static class FailCondition extends SimpleCondition<LoginEvent> {

        @Override
        public boolean filter(LoginEvent value) throws Exception {
            return value.getEventType().equals("fail");
        }
    }

    public static List<LoginEvent> buildLoginEnents() {
        List<LoginEvent> loginEvents = new ArrayList<>();
        loginEvents.add(new LoginEvent(1L, "张三", "fail", 1577080457L));
        loginEvents.add(new LoginEvent(2L, "张三", "fail", 1577080458L));
        loginEvents.add(new LoginEvent(3L, "张三", "fail", 1577080460L));
        loginEvents.add(new LoginEvent(4L, "李四", "fail", 1577080458L));
        loginEvents.add(new LoginEvent(5L, "李四", "success", 1577080462L));
        loginEvents.add(new LoginEvent(6L, "张三", "fail", 1577080462L));
        return loginEvents;
    }
}
