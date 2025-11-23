package com.atguigu.schedule.listener;

import com.atguigu.schedule.util.JDBCUtil;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 应用上下文监听器
 * 
 * 作用：
 * 1. 在应用关闭时优雅地关闭数据库连接池
 * 2. 清理 MySQL 驱动的后台线程
 * 3. 防止内存泄漏警告
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 应用启动时的逻辑（如果需要）
        System.out.println("应用启动...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 应用关闭时的逻辑
        System.out.println("应用关闭，正在清理资源...");
        
        try {
            // 1. 关闭 Druid 连接池
            JDBCUtil.closeDataSource();
            System.out.println("Druid 连接池已关闭");
            
            // 2. 清理 MySQL 驱动的后台线程
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL 清理线程已停止");
            
        } catch (Exception e) {
            System.err.println("资源清理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
