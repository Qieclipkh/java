package com.cly.service.scheduled;

import com.alibaba.druid.util.JdbcUtils;
import com.cly.data.DataSourceUtil;
import com.cly.entity.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Configuration
public class DatasourceConfig {

    @Bean
    public DataSource dataSource(){
        Database database = new Database();
        database.setDriverClassName("org.postgresql.Driver");
        database.setUrl("jdbc:postgresql://172.16.15.199:6543/db_gxpt?charset=cp936&ApplicationName=test");
        database.setUsername("gxpt");
        database.setPassword("123456");
        database.setName("199");
        return DataSourceUtil.buildDataSource(database);
    }

    @Bean("maxId")
    public Integer maxId(){
        Integer starupId = 0;
        DataSource dataSource = dataSource();
        final List<Map<String, Object>> maps;
        try {
            maps = JdbcUtils.executeQuery(dataSource, "select max(id) as maxid from db_data.t_source");
            if(maps.get(0).get("maxid") != null){
                starupId = Integer.parseInt(maps.get(0).get("maxid").toString());
            }
        } catch (SQLException throwables) {

        }
        return starupId;
    }
}
