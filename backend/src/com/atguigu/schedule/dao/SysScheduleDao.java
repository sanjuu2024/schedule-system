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
     * 用于删除指定sid的日程记录
     * @param sid
     * @return 返回影响数据库记录的行数，>0 说明删除成功，=0 说明删除失败
     */
    int deleteSchedule(int sid) throws Exception;

    /**
     * 用于更新指定日程记录
     * @param schedule 要更新的数据
     * @return 返回影响数据库记录的行数，>0 说明更新成功，=0 说明更新失败
     */
    int updateSchedule(SysSchedule schedule) throws Exception;

    /**
     * 查询所有用户的所有日程
     * @return 返回SysSchedule类型的集合
     */
    List<SysSchedule> findAllSchedules() throws Exception;

    /**
     * 查询指定用户的所有日程
     * @param uid 用户id
     * @return 返回SysSchedule类型的集合
     */
    List<SysSchedule> findSchedulesByUid(int uid) throws Exception;

    /**
     * 查询指定用户的分页日程列表
     * @param uid 用户id
     * @param page 当前页码
     * @param limit 每页显示的记录数
     * @return 返回SysSchedule类型的集合
     */
    List<SysSchedule> findSchedulesByUidAndPage(int uid, int page, int limit) throws Exception;

    /**
     * 查询指定用户的日程总数
     * @param uid 用户id
     * @return 返回总记录数
     */
    Long countSchedulesByUid(int uid) throws Exception;
}
