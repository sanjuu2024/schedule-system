// 使用请求与响应拦截器
import axios from 'axios';
import { getToken, removeToken } from './token';
import { ErrorCode } from '@/enums/ErrorCode';
import { ElMessage } from 'element-plus';

// 1.利用axios对象的create方法，创建axios实例（其他配置：基础路径、超时时间）
const request = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API, // 基础路径
    timeout: 5000, // 请求超时时间5s
});

// 2.request实例添加请求拦截器
request.interceptors.request.use((config) => {
    // 从 localStorage 获取 token
    const token = getToken();

    // 如果 token 存在，添加到请求头
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    // 必须返回配置对象否则报错
    return config;
});

// 3.request实例添加响应拦截器
request.interceptors.response.use(
    (res) => {
        // HTTP 2xx
        const data = res.data;

        // 检查业务状态码
        if (data.code === ErrorCode.SUCCESS) {
            // 业务成功，返回数据
            return data;
        } else {
            // 业务失败(HTTP 200 && code != 200)
            ElMessage.error(data.message || '操作失败');

            // 特殊业务码处理
            // token失效
            if (data.code === ErrorCode.TOKEN_INVALID) {
                removeToken();
                window.location.href = '/login';
            }
            return Promise.reject(data);
        }
    },
    (err) => {
        // HTTP 4xx/5xx
        let msg = '';

        // 检查是否有响应
        if (err.response) {
            const data = err.response.data;
            let status = err.response.status;

            // 优先使用后端返回的错误信息
            if (data?.message) {
                msg = data.message;
            } else {
                // 根据 HTTP 状态码提示
                switch (status) {
                    case 401:
                        msg = 'TOKEN过期或未登录';
                        // 清除 token 并跳转到登录页
                        removeToken();
                        window.location.href = '/login';
                        break;
                    case 403:
                        msg = '没有权限访问';
                        break;
                    case 404:
                        msg = '请求资源不存在';
                        break;
                    case 409:
                        msg = '资源冲突';
                        break;
                    case 500:
                        msg = '服务器错误，请稍后重试';
                        break;
                    default:
                        msg = '网络错误，请稍后重试';
                }
            }
        } else {
            // 没有响应（网络错误、超时等）
            msg = '网络连接失败，请检查网络';
        }

        // 提示错误信息
        ElMessage.error(msg);

        return Promise.reject(err);
    },
);

// 4.对外暴露request实例
export default request;
