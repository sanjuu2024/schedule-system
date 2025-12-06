package com.atguigu.schedule.controller.v1;

import com.atguigu.schedule.common.PageResult;
import com.atguigu.schedule.common.ResultCodeEnum;
import com.atguigu.schedule.pojo.SysSchedule;
import com.atguigu.schedule.service.SysScheduleService;
import com.atguigu.schedule.service.impl.SysScheduleServiceImpl;
import com.atguigu.schedule.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * 增加日程 push
 * 删除日程 delete
 * 修改日程 put
 * 查看日程 get
 */
@WebServlet("/v1/schedule/*")
public class SysScheduleController extends BaseController {

    private SysScheduleService sysScheduleService = new SysScheduleServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String method = req.getMethod();
//        System.out.println("pathInfo: "+pathInfo);
//        System.out.println("method: "+method);

        try {
//            System.out.println("asdf");
            // 路由分发
            if (pathInfo.startsWith("/list") && "GET".equals(method)) {
//                System.out.println("list");
                getScheduleListByPage(req, resp);
            } else if ("/add".equals(pathInfo) && "POST".equals(method)) {
//                System.out.println("add");
                add(req,resp);
            } else if ("/update".equals(pathInfo) && "PUT".equals(method)) {
//                System.out.println("update");
                update(req,resp);
            } else if (pathInfo.startsWith("/delete") && "DELETE".equals(method)) {
//                System.out.println("delete");
                delete(req,resp);
            }else {
//                System.out.println("asdf222");
                fail(resp, ResultCodeEnum.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(resp, ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SysSchedule schedule = readJSON(req, SysSchedule.class);
        try{
            int row = sysScheduleService.addSchedule(schedule);
            if (row > 0) {
                success(resp, "添加日程成功！");
            } else {
                fail(resp, ResultCodeEnum.SCHEDULE_ADD_FAIL);
            }
        } catch (Exception e){
            e.printStackTrace();
            fail(resp,ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SysSchedule schedule = readJSON(req, SysSchedule.class);
        try{
            int row = sysScheduleService.updateSchedule(schedule);
            if (row > 0) {
                success(resp, "更新日程成功！");
            } else {
                fail(resp, ResultCodeEnum.SCHEDULE_UPDATE_FAIL);
            }
        } catch (Exception e){
            e.printStackTrace();
            fail(resp,ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getPathInfo().split("/")[2]);
        try{
            int row = sysScheduleService.deleteSchedule(id);
            if (row > 0) {
                success(resp, "删除日程成功！");
            } else {
                fail(resp, ResultCodeEnum.SCHEDULE_DELETE_FAIL);
            }
        } catch (Exception e){
            e.printStackTrace();
            fail(resp,ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    protected void getScheduleListByPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String pathInfo = req.getPathInfo();
        String[] arr = pathInfo.split("/");
        int page = Integer.parseInt(arr[2]);
        int limit = Integer.parseInt(arr[3]);
        
        // 获取列表数据和总数
        String token = req.getHeader("Authorization").substring(7);
        Integer uid = 1;
        try {
            uid = JWTUtil.getUserIdFromToken(token);
        } catch (JwtException e) {
            fail(resp, ResultCodeEnum.TOKEN_INVALID, "无效的令牌");
        }
//        System.out.println("uid: "+uid);
        Long total = sysScheduleService.getScheduleCount(uid);
        List<SysSchedule> scheduleList = sysScheduleService.getScheduleListByUidAndPage(uid,page, limit);
        
        // 封装分页结果
        PageResult<SysSchedule> pageResult = PageResult.of(scheduleList, total, page, limit);
        success(resp, "查询成功！", pageResult);
    }

}
