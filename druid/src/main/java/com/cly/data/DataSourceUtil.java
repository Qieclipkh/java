package com.cly.data;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cly.entity.Database;
import org.springframework.cglib.beans.BeanMap;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceUtil {
    // org.postgresql.Driver
    public static DataSource buildDataSource(Database database){
        BeanMap beanMap = BeanMap.create(database);
        javax.sql.DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(beanMap);
        } catch (Exception e) {
        }
        return dataSource;
    }
}
