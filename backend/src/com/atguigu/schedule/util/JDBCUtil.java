package com.atguigu.schedule.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    // 🍉创建连接池引用，因为要提供给当前项目的全局使用，所以创建为静态的。
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    // 🍉项目启动时，即拆功能键连接池对象，赋值给dataSource
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 🍉对外提供在连接池中获取连接的方法
    public static Connection getConnection(){
        try{
            Connection connection = threadLocal.get();
            if (connection == null){
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // 🍉对外提供回收连接的方法
    public static void release(){
        try {
            Connection connection = threadLocal.get();
            if (connection != null){
                // 1.从threadLocal中移除当前已经存储的Connection对象
                threadLocal.remove();
                // 2.如果开启了事务的手动提交，操作完毕后归还给连接池之前，要把自动提交设置为true
                connection.setAutoCommit(true);
                // 3.将Connection对象归还给连接池
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}