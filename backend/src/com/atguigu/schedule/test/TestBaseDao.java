package com.atguigu.schedule.test;

import com.atguigu.schedule.dao.BaseDao;
import com.atguigu.schedule.pojo.SysUser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TestBaseDao {
    private static BaseDao baseDao;

    @BeforeClass
    public static void init() {
        baseDao = new BaseDao();
    }

    @Test
    public void testBaseDaoQuery() throws Exception {
        String sql1 = "select count(*) from sys_user";
        String sql2 = "select uid,username,user_pwd userPwd from sys_user";
        Long count = baseDao.executeQueryScalar(Long.class, sql1);
        List<SysUser> list = baseDao.executeQuery(SysUser.class,sql2);
        System.out.println("count: "+count);
        for (SysUser sysUser : list) {
            System.out.println("uid: "+sysUser.getUid()+", username: "+sysUser.getUsername()+", user_pwd: "+sysUser.getUserPwd());
        }
    }

    @Test
    public void testBaseDaoUpdate() throws Exception {
        String sql = "insert into sys_schedule values(DEFAULT,?,?,?)";
        int row = baseDao.executeUpdate(sql,1,"学习java",0);
        System.out.println("row: "+row);
    }
}
