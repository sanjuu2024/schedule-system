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
     * @param user 用户信息（包含用户名和密码）
     * @return 登录成功返回用户对象，失败返回null
     */
    SysUser login(SysUser user);

    /**
     * 根据用户ID查询用户信息
     * @param uid 用户ID
     * @return 用户对象
     */
    SysUser findByUid(Integer uid);
}
