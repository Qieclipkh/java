package com.cly.service.scheduled;

import com.alibaba.druid.util.JdbcUtils;
import com.cly.data.DataSourceUtil;
import com.cly.entity.Database;
import com.cly.runner.AppRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateData {

    @Autowired
    private Integer maxId;


    @Autowired
    private DataSource dataSource;

    private Integer currId;

    @Scheduled(cron = "0/30 * * * * ?")
    public void test() {
        if (currId == null) {
            currId = maxId;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Map<String, Object> data = new HashMap<>();
        currId++;
        final Integer id = currId;
        System.out.println(format.format(date)+"======"+id);
        data.put("id", id);
        data.put("name", "tom" + id);
        data.put("age", 11);
        data.put("address", "北京市");
        data.put("cjsj", new java.sql.Timestamp(System.currentTimeMillis()));
        try {
            JdbcUtils.insertToTable(dataSource, "db_data.t_source", data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
