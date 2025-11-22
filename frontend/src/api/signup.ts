// 注册相关接口
import request from '@/utils/request';
import type { SignupParams } from './type';

const API = {
    SIGNUP_URL: '/api/signup',
    CHECK_USERNAME_URL: '/api/userServlet',
};

// 注册接口
export const reqSignup = (data: SignupParams) => {
    return request.post<SignupParams, any>(API.SIGNUP_URL, data);
};

// 查询用户名是否重复接口
export const reqCheckUsername = (username: string) => {
    return request.get<any, any>(
        `${API.CHECK_USERNAME_URL}?username=${username}`,
    );
};
