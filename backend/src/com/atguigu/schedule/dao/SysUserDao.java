package com.atguigu.schedule.dao;

import com.atguigu.schedule.pojo.SysUser;

/**
 * @author sanjuu
 */
public interface SysUserDao{
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    SysUser findByUsername(String username) throws Exception;
    
    /**
     * 插入用户记录
     * @param user 用户信息
     * @return 影响的行数
     */
    int insert(SysUser user) throws Exception;
}
