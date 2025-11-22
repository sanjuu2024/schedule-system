// 注册相关接口
import request from '@/utils/request';
import type { RegisterParams } from './type';

const API = {
    SIGNUP_URL: '/v1/user/register',
    CHECK_USERNAME_URL: '/v1/user/check',
};

// 注册接口
export const reqRegister = async (data: RegisterParams) => {
    return await request.post<RegisterParams, any>(API.SIGNUP_URL, data);
};

// 查询用户名是否重复接口
export const reqCheckUsername = async (username: string) => {
    return await request.get<any, any>(
        `${API.CHECK_USERNAME_URL}?username=${username}`,
    );
};
