package com.atguigu.schedule.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("GET")) {
            view(req,resp);
        }
        else if (method.equals("POST")) {
            add(req,resp);
        }
        else if (method.equals("PUT")) {
            update(req,resp);
        }
        else if (method.equals("DELETE")) {
            delete(req,resp);
        }
        else{
            resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    protected abstract void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    protected abstract void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    protected abstract void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    protected abstract void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
