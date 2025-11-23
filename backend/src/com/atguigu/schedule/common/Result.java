package com.atguigu.schedule.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 全局统一响应的 JSON 格式类
 * 
 * 1. 实现 Serializable 接口，支持序列化
 * 2. 添加 timestamp 字段，记录响应时间
 * 3. 使用 @JsonInclude 注解，data 为 null 时不序列化
 * 4. 提供链式调用的 Builder 模式
 * 5. 提供丰富的静态工厂方法
 * 
 * @param <T> 响应数据类型
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer code;   // 业务状态码
    private String message;   // 响应消息
    private T data;   // 响应数据
    private Long timestamp;   // 响应时间戳

    // ==================== 构造方法 ====================
    
    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ==================== 成功响应 ====================
    
    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }
    
    /**
     * 成功响应（自定义消息）
     */
    public static <T> Result<T> ok(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }
    
    /**
     * 成功响应（自定义消息 + 数据）
     */
    public static <T> Result<T> ok(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // ==================== 失败响应 ====================
    
    /**
     * 失败响应（使用枚举）
     */
    public static <T> Result<T> fail(ResultCodeEnum codeEnum) {
        return new Result<>(codeEnum.getCode(), codeEnum.getMessage(), null);
    }

    /**
     * 失败响应（使用枚举 + 自定义消息）
     */
    public static <T> Result<T> fail(ResultCodeEnum codeEnum, String message) {
        return new Result<>(codeEnum.getCode(), message, null);
    }
    
    /**
     * 失败响应（自定义业务码和消息）
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 失败响应（默认使用系统错误）
     */
    public static <T> Result<T> fail() {
        return fail(ResultCodeEnum.SYSTEM_ERROR);
    }
    
    /**
     * 失败响应（自定义消息，使用默认系统错误码）
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCodeEnum.SYSTEM_ERROR.getCode(), message, null);
    }

    // ==================== 链式调用方法 ====================
    
    /**
     * 设置消息（链式调用）
     */
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置业务码（链式调用）
     */
    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }
    
    /**
     * 设置数据（链式调用）
     */
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    // ==================== 判断方法 ====================
    
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getCode().equals(this.code);
    }
    
    /**
     * 判断是否失败
     */
    public boolean isFail() {
        return !isSuccess();
    }

    // ==================== Getter & Setter ====================
    
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
