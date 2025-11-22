package com.atguigu.schedule.test;

import com.atguigu.schedule.dao.SysScheduleDao;
import com.atguigu.schedule.dao.impl.SysScheduleDaoImpl;
import com.atguigu.schedule.pojo.SysSchedule;
import org.junit.Test;

import java.util.List;

public class TestSysScheduleDao {
    public static SysScheduleDao ssd = new SysScheduleDaoImpl();

    @Test
    public void findAllSchedules() throws Exception {
        List<SysSchedule> list = ssd.findAllSchedules();
        for (SysSchedule schedule : list) {
            System.out.println("sid = " + schedule.getSid()+", uid = "+schedule.getUid()+", title = "+schedule.getTitle()+", completed = "+schedule.getCompleted());
        }
    }

    @Test
    public void findSchedulesByUid() throws Exception {
        List<SysSchedule> list = ssd.findSchedulesByUid(1);
        for (SysSchedule schedule : list) {
            System.out.println("sid = " + schedule.getSid()+", uid = "+schedule.getUid()+", title = "+schedule.getTitle()+", completed = "+schedule.getCompleted());
        }
    }

    @Test
    public void addSchedule() throws Exception {
        int rows = ssd.addSchedule(new SysSchedule(null,2,"学习JDBC",1));
        if (rows > 0) {
            System.out.println("添加日程成功！");
        } else {
            System.out.println("添加日程失败！");
        }
    }
}
