package com.atguigu.schedule.controller;

import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.service.impl.SysUserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/v1/user/*")
public class SysUserController extends BaseController {
    private SysUserService sysUserService = new SysUserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置跨域请求头
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setHeader("Access-Control-Max-Age", "3600");
        
        String pathInfo = req.getPathInfo();
        String method = req.getMethod();
        
        try {
            // 路由分发
            if ("/check".equals(pathInfo) && "GET".equals(method)) {
                checkUsername(req, resp);
            } else if ("/register".equals(pathInfo) && "POST".equals(method)) {
                register(req, resp);
            } else if ("/login".equals(pathInfo) && "POST".equals(method)) {
                login(req, resp);
            } else {
                error(resp, 404, "接口不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            error(resp, 500, "服务器内部错误");
        }
    }

    /**
     * 检查用户名是否存在
     */
    protected void checkUsername(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        boolean exists = sysUserService.checkUsername(username);
        success(resp, "查询成功", exists);
    }

    /**
     * 用户注册
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SysUser user = readJSON(req, SysUser.class);
        boolean result = sysUserService.register(user);
        
        if (result) {
            success(resp, "注册成功", null);
        } else {
            error(resp, 400, "注册失败，用户名已存在");
        }
    }
    
    /**
     * 用户登录
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SysUser user = readJSON(req,SysUser.class);
        boolean result = sysUserService.login(user);
        if (result){
            success(resp,"登录成功",null);
        } else{
            error(resp,400,"登录失败，用户名或密码错误");
        }
    }
}
