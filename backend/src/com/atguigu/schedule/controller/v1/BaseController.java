package com.atguigu.schedule.controller.v1;

import com.atguigu.schedule.common.Result;
import com.atguigu.schedule.common.ResultCodeEnum;
import com.atguigu.schedule.util.JsonUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 基础控制器，提供通用的响应处理方法
 * 
 * 1. JSON 处理委托给 JsonUtil 工具类（全局复用）
 * 2. 统一响应格式（使用 Result）
 * 3. 简化成功/失败响应方法
 * 4. HTTP 状态码与业务码自动映射
 */
public abstract class BaseController extends HttpServlet {

    // ==================== JSON 处理（委托给工具类）====================
    
    /**
     * 从请求体中读取 JSON 并转换为指定类型的对象
     */
    protected <T> T readJSON(HttpServletRequest req, Class<T> clazz) throws IOException {
        return JsonUtil.parseObject(req.getReader(), clazz);
    }

    /**
     * 将对象转换为 JSON 并写入响应
     */
    protected void writeJSON(HttpServletResponse resp, Object obj) throws IOException {
        JsonUtil.writeJson(resp, obj);
    }
    
    // ==================== 成功响应 ====================
    
    /**
     * 返回成功响应（带数据）
     */
    protected <T> void success(HttpServletResponse resp, T data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        writeJSON(resp, Result.ok(data));
    }
    
    /**
     * 返回成功响应（无数据）
     */
    protected void success(HttpServletResponse resp) throws IOException {
        success(resp, null);
    }
    
    /**
     * 返回成功响应（自定义消息 + 数据）
     */
    protected <T> void success(HttpServletResponse resp, String message, T data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        writeJSON(resp, Result.ok(message, data));
    }
    
    /**
     * 返回成功响应（自定义消息）
     */
    protected void success(HttpServletResponse resp, String message) throws IOException {
        success(resp, message, null);
    }
    
    // ==================== 失败响应 ====================
    
    /**
     * 返回失败响应（使用枚举）
     */
    protected void fail(HttpServletResponse resp, ResultCodeEnum resultCodeEnum) throws IOException {
        int httpStatus = getHttpStatusByEnum(resultCodeEnum);
        resp.setStatus(httpStatus);
        writeJSON(resp, Result.fail(resultCodeEnum));
    }
    
    /**
     * 返回失败响应（使用枚举 + 自定义消息）
     */
    protected void fail(HttpServletResponse resp, ResultCodeEnum resultCodeEnum, String message) throws IOException {
        int httpStatus = getHttpStatusByEnum(resultCodeEnum);
        resp.setStatus(httpStatus);
        writeJSON(resp, Result.fail(resultCodeEnum, message));
    }
    
    /**
     * 返回失败响应（自定义业务码和消息）
     */
    protected void fail(HttpServletResponse resp, Integer code, String message) throws IOException {
        int httpStatus = getHttpStatusByCode(code);
        resp.setStatus(httpStatus);
        writeJSON(resp, Result.fail(code, message));
    }
    
    /**
     * 返回失败响应（自定义 HTTP 状态码、业务码和消息）
     */
    protected void fail(HttpServletResponse resp, int httpStatus, Integer code, String message) throws IOException {
        resp.setStatus(httpStatus);
        writeJSON(resp, Result.fail(code, message));
    }

    // ==================== 辅助方法 ====================
    
    /**
     * 根据枚举获取对应的 HTTP 状态码
     */
    private int getHttpStatusByEnum(ResultCodeEnum resultCodeEnum) {
        Integer code = resultCodeEnum.getCode();
        return getHttpStatusByCode(code);
    }
    
    /**
     * 🔺🔺🔺根据业务码获取对应的 HTTP 状态码
     */
    private int getHttpStatusByCode(Integer code) {
        if (code == null) {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        
        // HTTP 标准状态码（200-599）
        if (code >= 200 && code < 600) {
            return code;
        }
        
        // 业务状态码映射
        if (code >= 1000 && code < 2000) {
            // 用户模块：400 或 401
            if (code == 1003) { // 用户名或密码错误
                return HttpServletResponse.SC_UNAUTHORIZED;
            }
            return HttpServletResponse.SC_BAD_REQUEST;
        } else if (code >= 2000 && code < 3000) {
            // 日程模块：404 或 400
            if (code == 2001) { // 日程不存在
                return HttpServletResponse.SC_NOT_FOUND;
            }
            return HttpServletResponse.SC_BAD_REQUEST;
        } else if (code >= 3000 && code < 4000) {
            // 认证授权模块：401 或 403
            if (code == 3005) { // 无访问权限
                return HttpServletResponse.SC_FORBIDDEN;
            }
            return HttpServletResponse.SC_UNAUTHORIZED;
        } else if (code >= 4000 && code < 5000) {
            // 参数校验模块：400
            return HttpServletResponse.SC_BAD_REQUEST;
        } else {
            // 系统错误：500
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }
    
    /**
     * 兼容旧版本的 error 方法（已废弃，建议使用 fail）
     * @deprecated 使用 fail 方法代替
     */
    @Deprecated
    protected void error(HttpServletResponse resp, int httpStatus, ResultCodeEnum resultCodeEnum, String message) throws IOException {
        fail(resp, httpStatus, resultCodeEnum.getCode(), message);
    }
    
    /**
     * 兼容旧版本的 error 方法（已废弃，建议使用 fail）
     * @deprecated 使用 fail 方法代替
     */
    @Deprecated
    protected void error(HttpServletResponse resp, int code, String message) throws IOException {
        fail(resp, code, message);
    }
    
    /**
     * 兼容旧版本的 error 方法（已废弃，建议使用 fail）
     * @deprecated 使用 fail 方法代替
     */
    @Deprecated
    protected void error(HttpServletResponse resp, int httpStatus, int code, String message) throws IOException {
        fail(resp, httpStatus, code, message);
    }
}
