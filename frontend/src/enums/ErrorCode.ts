/**
 * 业务状态码枚举
 * 与后端保持一致
 */
export const ErrorCode = {
    /** 成功 */
    SUCCESS: 200,

    // 1xxx 用户相关错误
    /** 用户名已存在 */
    USERNAME_EXIST: 1001,
    /** 用户名或密码错误 */
    LOGIN_ERROR: 1002,
    /** Token 无效 */
    TOKEN_INVALID: 1003,
    // 9xxx 系统错误
    /** 服务器内部错误 */
    SYSTEM_ERROR: 9999,
};
