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
    public List<SysSchedule> findAllSchedules() throws Exception {
        String sql = "select sid,uid,title,completed from sys_schedule";
        return executeQuery(SysSchedule.class,sql);
    }

    @Override
    public List<SysSchedule> findSchedulesByUid(int uid) throws Exception {
        String sql = "select sid,uid,title,completed from sys_schedule where uid = ?";
        return executeQuery(SysSchedule.class, sql, uid);
    }
}
