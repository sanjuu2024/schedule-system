package com.atguigu.schedule.service.impl;

import com.atguigu.schedule.dao.SysUserDao;
import com.atguigu.schedule.dao.impl.SysUserDaoImpl;
import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.util.MD5Util;

public class SysUserServiceImpl implements SysUserService {
    private SysUserDao sysUserDao = new SysUserDaoImpl();
    
    @Override
    public boolean checkUsername(String username) {
        try {
            return sysUserDao.findByUsername(username) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean register(SysUser user) {
        try {
            // 1. 检查用户名是否已存在
            if (checkUsername(user.getUsername())) {
                return false;   // 注册失败，用户名已存在
            }
            
            // 2. 对密码进行 MD5 加密
            String encryptedPwd = MD5Util.encrypt(user.getUserPwd());
            user.setUserPwd(encryptedPwd);
            
            // 3. 调用 DAO 层插入数据
            int rows = sysUserDao.insert(user);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean login(SysUser user) {
        try{
            // 1. 判断用户名是否存在
            if (!checkUsername(user.getUsername())) {
                return false;   // 用户名不存在，登录失败
            }

            // 2. 判断密码是否符合
            String encryptedPwd = MD5Util.encrypt(user.getUserPwd());
            SysUser existingUser = sysUserDao.findByUsername(user.getUsername());
            return existingUser.getUserPwd().equals(encryptedPwd);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
