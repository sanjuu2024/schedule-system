/**
 * 全局业务状态码枚举
 * 与后端 ResultCodeEnum 保持一致
 *
 * 1. HTTP 标准状态码（200, 400, 401, 403, 404, 500 等）
 * 2. 业务状态码分段管理：
 *    - 1xxx: 用户相关
 *    - 2xxx: 日程相关
 *    - 3xxx: 权限相关
 *    - 9xxx: 系统错误
 */
export const ErrorCode = {
    // ==================== 通用状态码 ====================
    /** 操作成功 */
    SUCCESS: 200,

    // ==================== HTTP 标准错误码 ====================
    /** 请求参数错误 */
    BAD_REQUEST: 400,
    /** 未授权，请先登录 */
    UNAUTHORIZED: 401,
    /** 禁止访问 */
    FORBIDDEN: 403,
    /** 请求资源不存在 */
    NOT_FOUND: 404,
    /** 请求方法不支持 */
    METHOD_NOT_ALLOWED: 405,
    /** 资源冲突 */
    CONFLICT: 409,
    /** 服务器内部错误 */
    INTERNAL_ERROR: 500,

    // ==================== 用户模块（1xxx）====================
    /** 用户不存在 */
    USER_NOT_FOUND: 1001,
    /** 用户名已存在 */
    USERNAME_EXIST: 1002,
    /** 用户名或密码错误 */
    USERNAME_OR_PASSWORD_ERROR: 1003,
    /** 账户已被锁定 */
    USER_ACCOUNT_LOCKED: 1004,
    /** 账户已被禁用 */
    USER_ACCOUNT_DISABLED: 1005,
    /** 密码错误 */
    PASSWORD_ERROR: 1006,
    /** 原密码错误 */
    OLD_PASSWORD_ERROR: 1007,
    /** 用户名不能为空 */
    USERNAME_EMPTY: 1008,
    /** 密码不能为空 */
    PASSWORD_EMPTY: 1009,

    // ==================== 日程模块（2xxx）====================
    /** 日程不存在 */
    SCHEDULE_NOT_FOUND: 2001,
    /** 日程标题不能为空 */
    SCHEDULE_TITLE_EMPTY: 2002,
    /** 日程时间无效 */
    SCHEDULE_TIME_INVALID: 2003,
    /** 日程删除失败 */
    SCHEDULE_DELETE_FAIL: 2004,
    /** 日程更新失败 */
    SCHEDULE_UPDATE_FAIL: 2005,
    /** 日程添加失败 */
    SCHEDULE_ADD_FAIL: 2006,

    // ==================== 认证授权模块（3xxx）====================
    /** Token 不能为空 */
    TOKEN_EMPTY: 3001,
    /** Token 无效或已过期 */
    TOKEN_INVALID: 3002,
    /** Token 已过期 */
    TOKEN_EXPIRED: 3003,
    /** Token 解析失败 */
    TOKEN_PARSE_ERROR: 3004,
    /** 无访问权限 */
    NO_PERMISSION: 3005,

    // ==================== 参数校验模块（4xxx）====================
    /** 参数不能为空 */
    PARAM_EMPTY: 4001,
    /** 参数格式不正确 */
    PARAM_INVALID: 4002,
    /** 参数类型错误 */
    PARAM_TYPE_ERROR: 4003,

    // ==================== 系统错误（9xxx）====================
    /** 系统繁忙，请稍后重试 */
    SYSTEM_ERROR: 9999,
    /** 数据库异常 */
    DATABASE_ERROR: 9998,
    /** 网络异常 */
    NETWORK_ERROR: 9997,
    /** 文件上传失败 */
    FILE_UPLOAD_ERROR: 9996,
    /** 文件下载失败 */
    FILE_DOWNLOAD_ERROR: 9995,
} as const;

/**
 * 错误码类型
 */
export type ErrorCodeType = (typeof ErrorCode)[keyof typeof ErrorCode];
