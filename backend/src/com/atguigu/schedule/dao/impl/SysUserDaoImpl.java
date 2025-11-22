package com.atguigu.schedule.dao.impl;

import com.atguigu.schedule.dao.BaseDao;
import com.atguigu.schedule.dao.SysUserDao;
import com.atguigu.schedule.pojo.SysUser;

import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {
    @Override
    public SysUser findByUsername(String username) throws Exception {
        String sql = "select * from sys_user where username=?";
        return executeQuerySingle(SysUser.class, sql, username);
    }
    
    @Override
    public int insert(SysUser user) throws Exception {
        String sql = "insert into sys_user (username, user_pwd) values (?, ?)";
        return executeUpdate(sql, user.getUsername(), user.getUserPwd());
    }
}
