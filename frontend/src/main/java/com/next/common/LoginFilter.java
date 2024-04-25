package com.next.common;

import com.next.model.TrainUser;
import com.next.util.JsonMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        TrainUser trainUser = (TrainUser)request.getSession().getAttribute("user");
        if (trainUser == null) {
            JsonData jsonData = JsonData.fail(ErrorCode.USER_NOT_LOGIN.getId(), ErrorCode.USER_NOT_LOGIN.getDesc());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
            return;
        }
        RequestHolder.add(trainUser);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
