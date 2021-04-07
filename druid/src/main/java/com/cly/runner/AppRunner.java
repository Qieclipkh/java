package com.cly.runner;

import com.alibaba.druid.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/*@Component
@Order*/
public class AppRunner implements CommandLineRunner {

    private Integer starupId = 0;
    @Autowired
    private DataSource dataSource;
    @Override
    public void run(String... args) throws Exception {
        final List<Map<String, Object>> maps = JdbcUtils.executeQuery(dataSource, "select max(id) as maxid from db_data.t_source");
        Integer starupId = 0;
        if(maps.get(0).get("maxid") != null){
            starupId = Integer.parseInt(maps.get(0).get("maxid").toString());
        }
    }

    public Integer getStarupId() {
        return starupId;
    }
}
