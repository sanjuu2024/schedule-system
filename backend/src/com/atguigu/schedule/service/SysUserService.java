package com.atguigu.schedule.service;

import com.atguigu.schedule.pojo.SysUser;

/**
 * 该接口定义了以sys_user表格为核心的业务操作
 */
public interface SysUserService {
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return true-已存在，false-不存在
     */
    boolean checkUsername(String username);
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return true-注册成功，false-注册失败
     */
    boolean register(SysUser user);

    /**
     * 用户登录
     * @param user 用户信息
     * @return true-登录成功，false-登录失败
     */
    boolean login(SysUser user);
}
