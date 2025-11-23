// 登录相关接口
import request from '@/utils/request';
import type { RegisterParams, LoginParams } from './type';

const API = {
    CHECK_USERNAME_URL: '/v1/user/check',
    SIGNUP_URL: '/v1/user/register',
    LOGIN_URL: '/v1/user/login',
    USER_INFO_URL: '/v1/user/info',
    LOGOUT_URL: '/v1/user/logout',
};

// 查询用户名是否重复接口
export const reqCheckUsername = async (username: string) => {
    return await request.get<any, any>(
        `${API.CHECK_USERNAME_URL}?username=${username}`,
    );
};

// 注册接口
export const reqRegister = async (data: RegisterParams) => {
    return await request.post<RegisterParams, any>(API.SIGNUP_URL, data);
};

// 登录接口
export const reqLogin = async (data: LoginParams) => {
    return await request.post<LoginParams, any>(API.LOGIN_URL, data);
};

// 获取用户信息接口
export const reqUserInfo = async () => {
    return await request.get<any, any>(API.USER_INFO_URL);
};

// 退出登录接口
export const reqLogout = async () => {
    return await request.post<any, any>(API.LOGOUT_URL);
};
