package com.atguigu.schedule.service.impl;

import com.atguigu.schedule.dao.SysUserDao;
import com.atguigu.schedule.dao.impl.SysUserDaoImpl;
import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.util.JWTUtil;
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
    public SysUser login(SysUser user) {
        try {
            // 1. 根据用户名查询用户
            SysUser existingUser = sysUserDao.findByUsername(user.getUsername());
            if (existingUser == null) {
                return null;   // 用户名不存在，登录失败
            }

            // 2. 验证密码
            String encryptedPwd = MD5Util.encrypt(user.getUserPwd());
            if (existingUser.getUserPwd().equals(encryptedPwd)) {
                // 密码正确，返回用户对象（🔺🔺🔺不包含密码）
                existingUser.setUserPwd(null);
                return existingUser;
            }
            
            return null;  // 密码错误
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SysUser findByUid(Integer uid) {
        try {
            SysUser user = sysUserDao.findByUid(uid);
            if (user != null) {
                user.setUserPwd(null);  // 不返回密码
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
