package com.collinsceleb.campaign_management.config;

import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;
import com.collinsceleb.campaign_management.security.CustomUSerDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProfileInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUSerDetails customUserDetails) {
            UserEntity user = customUserDetails.getUser();
            if (user.getFirstName() == null || user.getLastName() == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("""
                            {
                                "message": "Please update your profile before accessing this resource."
                            }
                        """);
                return false;
            }
        }
        return true;
    }
}
