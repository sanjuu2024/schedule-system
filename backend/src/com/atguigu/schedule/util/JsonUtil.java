package com.atguigu.schedule.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * JSON 工具类
 * 
 * 1. 统一 JSON 序列化/反序列化逻辑
 * 2. 全局可用（Controller、Filter、Interceptor 等）
 * 3. 异常统一处理
 * 4. 支持多种使用场景
 */
public class JsonUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // ==================== 对象 -> JSON 字符串 ====================
    
    /**
     * 将对象转换为 JSON 字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转 JSON 失败", e);
        }
    }
    
    /**
     * 将对象转换为格式化的 JSON 字符串（带缩进）
     */
    public static String toPrettyJsonString(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转 JSON 失败", e);
        }
    }

    // ==================== JSON 字符串 -> 对象 ====================
    
    /**
     * 将 JSON 字符串转换为对象
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 转对象失败", e);
        }
    }
    
    /**
     * 从 BufferedReader 读取 JSON 并转换为对象
     */
    public static <T> T parseObject(BufferedReader reader, Class<T> clazz) throws IOException {
        return objectMapper.readValue(reader, clazz);
    }

    // ==================== 写入 HTTP 响应 ====================
    
    /**
     * 将对象转换为 JSON 并写入 HTTP 响应
     * 
     * @param resp HTTP 响应对象
     * @param obj 要写入的对象
     */
    public static void writeJson(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String json = toJsonString(obj);
        resp.getWriter().write(json);
        resp.getWriter().flush();
    }
    
    /**
     * 将对象转换为 JSON 并写入 HTTP 响应（带状态码）
     */
    public static void writeJson(HttpServletResponse resp, int httpStatus, Object obj) throws IOException {
        resp.setStatus(httpStatus);
        writeJson(resp, obj);
    }

    // ==================== 获取 ObjectMapper ====================
    
    /**
     * 获取全局 ObjectMapper 实例
     * （用于自定义配置）
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
