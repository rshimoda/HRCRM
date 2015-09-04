package com.eisgroup.hrcrm.ui.beans;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlRewriteFilter implements Filter {

    public UrlRewriteFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
        }

        if (uri.contains("/browse/task-")) {
            String id = uri.substring(uri.lastIndexOf('-') + 1);

            String newURI = "/ViewTask.xhtml?taskId=" + id;
            servletRequest.getRequestDispatcher(newURI).forward(servletRequest, servletResponse);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

    @Override
    public void destroy() {
        //
    }
}