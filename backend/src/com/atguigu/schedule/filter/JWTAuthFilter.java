package com.atguigu.schedule.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.atguigu.schedule.util.JWTUtil;

import java.io.IOException;

@WebFilter("/*")
public class JWTAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        // 白名单
        String path = request.getRequestURI();
        if (path.contains("/login") || path.contains("/register") || path.contains("/check")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
                
            try{
                Claims claims = JWTUtil.validateToken(token);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception e) {
                sendErrorResponse(response,401, "Invalid or expired token");
            }
        }
        else{
            sendErrorResponse(response,401, "请先登录");
        }
    }

    private void sendErrorResponse(HttpServletResponse resp, int code, String message) 
            throws IOException {
        resp.setStatus(code);
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        // 手动构造 JSON 字符串
        String json = String.format(
            "{\"code\":%d,\"message\":\"%s\",\"data\":null}", 
            code, message
        );
        
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }
}
