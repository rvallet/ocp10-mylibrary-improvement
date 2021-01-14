package com.library.website.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DeniedAccessController implements AccessDeniedHandler {

        private static final Logger LOGGER = LoggerFactory.getLogger(DeniedAccessController.class);

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                LOGGER.info("{} a tenté de se connecté à l'URL {}", auth.getName(), request.getRequestURI());
            } else {
                LOGGER.info("Tentative d'accès à l'URL {]", request.getRequestURI());
            }
            response.sendRedirect(request.getContextPath() + "/access-denied");
        }
}
