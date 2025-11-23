package com.atguigu.schedule.common;

/**
 * 业务状态码常量
 * 与前端 ErrorCode 枚举保持一致
 */
public class ErrorCode {
    
    /** 成功 */
    public static final int SUCCESS = 200;
    
    // ========== 1xxx 用户相关错误 ==========
    
    /** 用户名已存在 */
    public static final int USERNAME_EXIST = 1001;
    
    /** 用户名或密码错误 */
    public static final int LOGIN_ERROR = 1002;
    
    /** Token 无效 */
    public static final int TOKEN_INVALID = 1003;
    
    // ========== 9xxx 系统错误 ==========
    
    /** 服务器内部错误 */
    public static final int SYSTEM_ERROR = 9999;
    
    private ErrorCode() {
        // 工具类不允许实例化
    }
}
