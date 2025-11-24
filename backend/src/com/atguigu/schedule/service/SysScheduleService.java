package com.atguigu.schedule.service;

import com.atguigu.schedule.pojo.SysSchedule;

import java.util.List;

/**
 * 该接口定义了以sys_schedule表格为核心的义务操作
 */
public interface SysScheduleService {
    /**
     * 用于获取分页日程列表
     * @param page 当前页码
     * @param limit 每页显示的记录数
     * @return 返回SysSchedule类型的集合
     */
    public List<SysSchedule> getScheduleListByPage(int page, int limit) throws Exception;

    /**
     * 用于获取指定用户的分页日程列表
     * @param uid 用户id
     * @param page 当前页码
     * @param limit 每页显示的记录数
     * @return 返回SysSchedule类型的集合
     * @throws Exception 抛出异常
     */
    public List<SysSchedule> getScheduleListByUidAndPage(int uid, int page, int limit) throws Exception;

    /**
     * 用于添加日程
     * @param schedule 以SysSchedule实体类的形式传入
     * @return 返回影响数据库记录的行数，>0 说明添加成功，=0 说明添加失败
     * @throws Exception 抛出异常
     */
    public int addSchedule(SysSchedule schedule) throws Exception;

    /**
     * 用于删除指定sid的日程记录
     * @param sid 日程id
     * @return 返回影响数据库记录的行数，>0 说明删除成功，=0 说明删除失败
     * @throws Exception 抛出异常
     */
    public int deleteSchedule(int sid) throws Exception;

    /**
     * 用于更新指定日程记录
     * @param schedule 要更新的数据
     * @return 返回影响数据库记录的行数，>0 说明更新成功，=0 说明更新失败
     * @throws Exception 抛出异常
     */
    public int updateSchedule(SysSchedule schedule) throws Exception;

    /**
     * 获取指定用户的日程总数
     * @param uid 用户id
     * @return 返回总记录数
     * @throws Exception 抛出异常
     */
    public Long getScheduleCount(int uid) throws Exception;
}
