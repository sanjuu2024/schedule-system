package com.atguigu.schedule.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 记录请求信息
        long startTime = System.currentTimeMillis();
        String method = req.getMethod();
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        String ip = getClientIP(req);

        // 包装 Response 以便获取响应状态
        ResponseWrapper responseWrapper = new ResponseWrapper(resp);

        try {
            // 执行请求
            chain.doFilter(request, responseWrapper);
        } finally {
            // 计算耗时
            long duration = System.currentTimeMillis() - startTime;
            int status = responseWrapper.getStatus();

            // 记录日志
            logger.info("Request: {} {} {} | IP: {} | Status: {} | Duration: {}ms",
                    method, uri, queryString != null ? "?" + queryString : "",
                    ip, status, duration);

            // 可选：保存到数据库
            // saveAccessLog(method, uri, ip, status, duration);
        }
    }

    private String getClientIP(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = req.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    private void saveAccessLog(String method, String uri, String ip, int status, long duration) {
        // 保存到数据库的逻辑
        // INSERT INTO access_log (method, uri, ip, status, duration, created_at) VALUES (...)
    }
}

// ResponseWrapper 用于获取响应状态
class ResponseWrapper extends HttpServletResponseWrapper {
    private int status = 200;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void setStatus(int sc) {
        this.status = sc;
        super.setStatus(sc);
    }

    public int getStatus() {
        return status;
    }
}
