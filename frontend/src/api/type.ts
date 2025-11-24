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

// 用户信息类型
export interface UserInfo {
    uid: number;
    username: string;
}

// 日程类型
export interface Schedule {
    sid: number;
    uid: number;
    title: string;
    completed: number; // 0-未完成, 1-已完成
}

// 分页数据类型
export interface PageResult<T> {
    records: T[];
    total: number;
    pageNum?: number;
    pageSize?: number;
    pages?: number;
}

// 请求日程列表的返回类型
export interface ScheduleListResponseData extends ResponseData {
    data: PageResult<Schedule>;
}
