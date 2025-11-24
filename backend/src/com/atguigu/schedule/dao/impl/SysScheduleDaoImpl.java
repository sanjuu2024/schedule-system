package com.atguigu.schedule.dao.impl;

import com.atguigu.schedule.dao.BaseDao;
import com.atguigu.schedule.dao.SysScheduleDao;
import com.atguigu.schedule.pojo.SysSchedule;

import java.util.List;

public class SysScheduleDaoImpl extends BaseDao implements SysScheduleDao {
    @Override
    public int addSchedule(SysSchedule schedule) throws Exception {
        String sql = "insert into sys_schedule values(DEFAULT,?,?,?)";
        return executeUpdate(sql,schedule.getUid(),schedule.getTitle(),schedule.getCompleted());
    }

    @Override
    public int deleteSchedule(int sid) throws Exception {
        String sql = "delete from sys_schedule where sid = ?";
        return  executeUpdate(sql,sid);
    }

    @Override
    public int updateSchedule(SysSchedule schedule) throws Exception {
        String sql = "update sys_schedule set uid = ?, title = ?, completed = ? where sid = ?";
        return executeUpdate(sql,schedule.getUid(),schedule.getTitle(),schedule.getCompleted(),schedule.getSid());
    }

    @Override
    public List<SysSchedule> findAllSchedules() throws Exception {
        String sql = "select sid,uid,title,completed from sys_schedule";
        return executeQuery(SysSchedule.class,sql);
    }

    @Override
    public List<SysSchedule> findSchedulesByUid(int uid) throws Exception {
        String sql = "select sid,uid,title,completed from sys_schedule where uid = ?";
        return executeQuery(SysSchedule.class, sql, uid);
    }

    @Override
    public List<SysSchedule> findSchedulesByUidAndPage(int uid, int page, int limit) throws Exception{
        String sql = "select sid,uid,title,completed from sys_schedule where uid = ? limit ?,?";
        int offset = (page - 1) * limit;
        return executeQuery(SysSchedule.class, sql, uid, offset, limit);
    }

    @Override
    public Long countSchedulesByUid(int uid) throws Exception {
        String sql = "select count(*) from sys_schedule where uid = ?";
        return executeQueryScalar(Long.class, sql, uid);
    }
}
