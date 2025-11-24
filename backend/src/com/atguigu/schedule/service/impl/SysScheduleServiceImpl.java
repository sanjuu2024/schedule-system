package com.atguigu.schedule.service.impl;

import com.atguigu.schedule.dao.SysScheduleDao;
import com.atguigu.schedule.dao.impl.SysScheduleDaoImpl;
import com.atguigu.schedule.pojo.SysSchedule;
import com.atguigu.schedule.service.SysScheduleService;

import java.util.List;

public class SysScheduleServiceImpl implements SysScheduleService {
    private SysScheduleDao sysScheduleDao = new SysScheduleDaoImpl();

    @Override
    public List<SysSchedule> getScheduleListByPage(int page, int limit) throws Exception{
        return sysScheduleDao.findSchedulesByUidAndPage(1, page, limit);
    }

    @Override
    public List<SysSchedule> getScheduleListByUidAndPage(int uid, int page, int limit) throws Exception {
        return sysScheduleDao.findSchedulesByUidAndPage(uid, page, limit);
    }

    @Override
    public int addSchedule(SysSchedule schedule) throws Exception {
        return sysScheduleDao.addSchedule(schedule);
    }

    @Override
    public int deleteSchedule(int sid) throws Exception {
        return sysScheduleDao.deleteSchedule(sid);
    }

    @Override
    public int updateSchedule(SysSchedule schedule) throws Exception {
        return sysScheduleDao.updateSchedule(schedule);
    }

    @Override
    public Long getScheduleCount(int uid) throws Exception {
        return sysScheduleDao.countSchedulesByUid(uid);
    }
}
