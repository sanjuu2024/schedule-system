package com.atguigu.schedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制器，提供通用的 JSON 处理工具方法
 */
public abstract class BaseController extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 从请求体中读取 JSON 并转换为指定类型的对象
     */
    protected <T> T readJSON(HttpServletRequest req, Class<T> clazz) throws IOException {
        BufferedReader reader = req.getReader();
        return objectMapper.readValue(reader, clazz);
    }

    /**
     * 将对象转换为 JSON 并写入响应
     */
    protected void writeJSON(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String json = objectMapper.writeValueAsString(obj);
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }
    
    /**
     * 返回成功响应
     */
    protected void success(HttpServletResponse resp, String message, Object data) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", message);
        result.put("data", data);
        writeJSON(resp, result);
    }
    
    /**
     * 返回失败响应
     */
    protected void error(HttpServletResponse resp, int code, String message) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        result.put("data", null);
        writeJSON(resp, result);
    }
}
