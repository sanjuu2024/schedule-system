// 登录相关接口
import request from '@/utils/request';
import type { LoginParams } from './type';

const API = {
    LOGIN_URL: '/v1/user/login',
};

// 登录接口
export const reqLogin = (data: LoginParams) => {
    return request.post<LoginParams, any>(API.LOGIN_URL, data);
};
