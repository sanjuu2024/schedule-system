package com.atguigu.schedule.controller.v1;

import com.atguigu.schedule.common.ResultCodeEnum;
import com.atguigu.schedule.pojo.SysUser;
import com.atguigu.schedule.service.SysUserService;
import com.atguigu.schedule.service.impl.SysUserServiceImpl;
import com.atguigu.schedule.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
            } else if ("/info".equals(pathInfo) && "GET".equals(method)) {
                userInfo(req,resp);
            } else if ("/logout".equals(pathInfo) && "POST".equals(method)) {
                logout(req,resp);
            } else {
                fail(resp, ResultCodeEnum.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(resp, ResultCodeEnum.SYSTEM_ERROR);
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
            success(resp, "注册成功");
        } else {
            fail(resp, ResultCodeEnum.USERNAME_EXIST);
        }
    }
    
    /**
     * 用户登录
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SysUser loginUser = readJSON(req, SysUser.class);
        
        // 调用 Service 层验证用户
        SysUser user = sysUserService.login(loginUser);
        
        if (user != null) {
            // 登录成功，生成 JWT Token
            String token = JWTUtil.generateToken(user.getUid(), user.getUsername());
            
            // 返回 token 和用户信息
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            
            success(resp, "登录成功", data);
        } else {
            fail(resp, ResultCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    /**
     * 用户信息
     */
    protected void userInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String authHeader = req.getHeader("Authorization");
        String token = authHeader.substring(7);
        try {
            Claims claims = JWTUtil.validateToken(token);
            Integer uid = claims.get("uid", Integer.class);
            String username = claims.get("username", String.class);

            Map<String, Object> data = new HashMap<>();
            data.put("uid", uid);
            data.put("username", username);

            success(resp, "获取成功", data);
        } catch (JwtException e) {
            fail(resp, ResultCodeEnum.TOKEN_INVALID, "无效的令牌");
        }
    }

    /**
     * 用户退出
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 由于 JWT 是无状态的，服务器端不保存会话信息，因此无法真正“注销”一个 JWT。
        // 通常的做法是让客户端删除存储的 JWT。
        success(resp, "退出成功");
    }
}
