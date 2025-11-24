// 日程相关接口
import request from '@/utils/request';
import type { ResponseData, Schedule, ScheduleListResponseData } from './type';

const API = {
    // 获取日程列表
    GET_SCHEDULE_LIST: 'v1/schedule/list', // /{page}/{size}
    // 添加日程
    ADD_SCHEDULE: 'v1/schedule/add',
    // 删除日程
    DELETE_SCHEDULE: 'v1/schedule/delete', // /{id}
    // 更新日程
    UPDATE_SCHEDULE: 'v1/schedule/update',
};

// 获取日程列表
export const reqScheduleList = async (page: number, size: number) => {
    return await request.get<any, ScheduleListResponseData>(
        `${API.GET_SCHEDULE_LIST}/${page}/${size}`,
    );
};

// 添加日程
export const reqAddSchedule = async (data: Schedule) => {
    return await request.post<Schedule, ResponseData>(API.ADD_SCHEDULE, data);
};

// 删除日程
export const reqDeleteSchedule = async (id: number) => {
    return await request.delete<any, ResponseData>(
        `${API.DELETE_SCHEDULE}/${id}`,
    );
};

// 更新日程
export const reqUpdateSchedule = async (data: Schedule) => {
    return await request.put<Schedule, ResponseData>(API.UPDATE_SCHEDULE, data);
};
