// 登录接口参数限制
export interface LoginParams {
    username: string;
    password: string;
}

// 注册接口参数限制
export interface RegisterParams {
    username: string;
    password: string;
}

// 返回结果
export interface ResponseData {
    code: number;
    message: string;
    data?: any;
}
