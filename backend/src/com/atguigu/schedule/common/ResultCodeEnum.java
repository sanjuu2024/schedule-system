package com.atguigu.schedule.common;

/**
 * 全局业务状态码枚举
 * 
 * 业界规范：
 * 1. HTTP 标准状态码（200, 400, 401, 403, 404, 500 等）
 * 2. 业务状态码分段管理：
 *    - 1xxx: 用户相关
 *    - 2xxx: 日程相关
 *    - 3xxx: 权限相关
 *    - 9xxx: 系统错误
 */
public enum ResultCodeEnum {
    
    // ==================== 通用状态码 ====================
    SUCCESS(200, "操作成功"),

    // ==================== HTTP 标准错误码 ====================
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    CONFLICT(409, "资源冲突"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    // ==================== 用户模块（1xxx）====================
    USER_NOT_FOUND(1001, "用户不存在"),
    USERNAME_EXIST(1002, "用户名已存在"),
    USERNAME_OR_PASSWORD_ERROR(1003, "用户名或密码错误"),
    USER_ACCOUNT_LOCKED(1004, "账户已被锁定"),
    USER_ACCOUNT_DISABLED(1005, "账户已被禁用"),
    PASSWORD_ERROR(1006, "密码错误"),
    OLD_PASSWORD_ERROR(1007, "原密码错误"),
    USERNAME_EMPTY(1008, "用户名不能为空"),
    PASSWORD_EMPTY(1009, "密码不能为空"),
    
    // ==================== 日程模块（2xxx）====================
    SCHEDULE_NOT_FOUND(2001, "日程不存在"),
    SCHEDULE_TITLE_EMPTY(2002, "日程标题不能为空"),
    SCHEDULE_TIME_INVALID(2003, "日程时间无效"),
    SCHEDULE_DELETE_FAIL(2004, "日程删除失败"),
    SCHEDULE_UPDATE_FAIL(2005, "日程更新失败"),
    SCHEDULE_ADD_FAIL(2006, "日程添加失败"),
    
    // ==================== 认证授权模块（3xxx）====================
    TOKEN_EMPTY(3001, "Token 不能为空"),
    TOKEN_INVALID(3002, "Token 无效或已过期"),
    TOKEN_EXPIRED(3003, "Token 已过期"),
    TOKEN_PARSE_ERROR(3004, "Token 解析失败"),
    NO_PERMISSION(3005, "无访问权限"),
    
    // ==================== 参数校验模块（4xxx）====================
    PARAM_EMPTY(4001, "参数不能为空"),
    PARAM_INVALID(4002, "参数格式不正确"),
    PARAM_TYPE_ERROR(4003, "参数类型错误"),
    
    // ==================== 系统错误（9xxx）====================
    SYSTEM_ERROR(9999, "系统繁忙，请稍后重试"),
    DATABASE_ERROR(9998, "数据库异常"),
    NETWORK_ERROR(9997, "网络异常"),
    FILE_UPLOAD_ERROR(9996, "文件上传失败"),
    FILE_DOWNLOAD_ERROR(9995, "文件下载失败");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
    /**
     * 根据 code 获取枚举
     */
    public static ResultCodeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResultCodeEnum codeEnum : ResultCodeEnum.values()) {
            if (codeEnum.getCode().equals(code)) {
                return codeEnum;
            }
        }
        return null;
    }
}
