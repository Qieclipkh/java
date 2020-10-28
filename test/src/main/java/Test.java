import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thunisoft.cloud.storage.client.base.config.StorageConfig;
import com.thunisoft.cloud.storage.client.minio.provider.MinioProvider;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

public class Test {
    private static  final Logger logger = LoggerFactory.getLogger("main");
    public static void main(String[] args) throws Exception {
        List<Student> list = new ArrayList<>();
        list.add(new Student(3,"tom",13));
        list.add(new Student(1,"tom1",15));
        list.add(new Student(2,"tom2",14));
        list.sort(Comparator.comparing(Student::getAge).reversed());
        list.forEach(m-> System.out.println(m));
/*
        MinioClient client = new MinioClient("http://172.16.34.106:9000","minio","6789@jkl");
        boolean source = client.bucketExists("source");
        System.out.println(source);


        InputStream in = client.getObject("source", "activate-power-mode_v0.1.8.jar");
        File file = new File("1.jar");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) != -1)
        {
            outputStream.write(bytes, 0, len);
        }
        in.close();
        outputStream.close();*/
       /* MinioProvider minioProvider = new MinioProvider();
        StorageConfig config = new StorageConfig();
        minioProvider.initByStorageConfig(config);*/

    }






















    public static void ff(){
            Properties props = new Properties();
            InputStream in = null;
            try {
                String fileName = "config.properties";
                File  file = new File(fileName);
                in = new FileInputStream(file);
                props.load(in);
                String driverClass =props.getProperty("driverClass");
                String jdbcurl = props.getProperty("jdbcurl");
                String username =props.getProperty("user");
                String password = props.getProperty("password");
                ComboPooledDataSource dataSource = new ComboPooledDataSource();
                dataSource.setDriverClass(driverClass);
                dataSource.setJdbcUrl(jdbcurl);
                dataSource.setUser(username);
                dataSource.setPassword(password);
                dataSource.setInitialPoolSize(5);
                dataSource.setMaxPoolSize(5);
                dataSource.setCheckoutTimeout(4000);
                dataSource.setTestConnectionOnCheckout(true);
                dataSource.setPreferredTestQuery("SELECT 1");

                Connection connection = dataSource.getConnection();
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println(metaData);
                ResultSet rs = metaData.getSchemas();
                while (rs.next()){
                    String name = rs.getString(1);
                    System.out.println(name);
                }

                System.out.println("==========================");

                rs = metaData.getCatalogs();
                while (rs.next()){
                    String name = rs.getString(1);
                    System.out.println(name);
                }
                rs.close();
                connection.close();

            } catch (Exception e) {
                logger.error("出错",e);
            }



    }
}
