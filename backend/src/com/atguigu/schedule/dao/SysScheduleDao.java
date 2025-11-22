package com.atguigu.schedule.dao;

import com.atguigu.schedule.pojo.SysSchedule;

import java.util.List;

/**
 * @author sanjuu
 */
public interface SysScheduleDao {
    /**
     * 用于向数据库中添加一条日程记录
     * @param schedule 以SysSchedule实体类的形式传入
     * @return 返回影响数据库记录的行数，>0 说明添加成功，=0 说明添加失败
     */
    int addSchedule(SysSchedule schedule) throws Exception;

    /**
     * 查询所有用户的所有日程
     * @return 返回SysSchedule类型的集合
     */
    List<SysSchedule> findAllSchedules() throws Exception;

    /**
     * 查询指定用户的所有日程
     * @return 返回SysSchedule类型的集合
     */
    List<SysSchedule> findSchedulesByUid(int uid) throws Exception;
}
