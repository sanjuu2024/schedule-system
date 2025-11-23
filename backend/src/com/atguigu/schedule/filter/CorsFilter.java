package com.atguigu.schedule.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 跨域过滤器
 * 
 * 1. 统一处理所有跨域请求
 * 2. 支持 OPTIONS 预检请求
 * 3. 配置安全的 CORS 策略
 * 
 * 为什么需要 OPTIONS 预检请求？
 * - 当请求包含自定义头（如 Authorization）时
 * - 当请求方法为 PUT、DELETE 时
 * - 浏览器会先发送 OPTIONS 请求确认是否允许跨域
 */
@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // 1. 允许的来源（生产环境应指定具体域名）
        resp.setHeader("Access-Control-Allow-Origin", "*");
        
        // 2. 允许的请求方法
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // 3. 允许的请求头（重要：包含 Authorization）
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // 4. 允许携带 Cookie（如果需要）
        // resp.setHeader("Access-Control-Allow-Credentials", "true");
        
        // 5. 预检请求缓存时间（1小时）
        resp.setHeader("Access-Control-Max-Age", "3600");
        
        // 6. 处理 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            // OPTIONS 请求直接返回 200，不继续处理
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
        // 7. 继续执行过滤器链
        chain.doFilter(request, response);
    }
}
