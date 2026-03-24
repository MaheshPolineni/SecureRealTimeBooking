package com.example.leisuires.components;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        // 1️⃣ Get existing session (don’t create a new one)
        HttpSession session = request.getSession(false);

        // 2️⃣ Check if session exists AND admin is logged in
        if (session != null && "ADMIN".equals(session.getAttribute("ADMIN_ROLE"))) {
            // ✅ Admin is logged in, allow request
            return true;
        }

        // 3️⃣ Admin not logged in
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Admin login required");
        return false;
    }
}
