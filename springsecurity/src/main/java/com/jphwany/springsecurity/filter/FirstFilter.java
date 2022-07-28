package com.jphwany.springsecurity.filter;

import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        Filter.super.init(filterConfig);
        System.out.println("FirstFilter Create");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{
        System.out.println("========First 필터 start========");
        chain.doFilter(request, response);
        System.out.println("========First 필터 end========");
    }

    @Override
    public void destroy() {
        System.out.println("FirstFilter Gone");
        Filter.super.destroy();
    }
}
