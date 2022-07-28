package com.jphwany.jwt.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException{

        HttpServletRequest reque = (HttpServletRequest) request;
        HttpServletResponse respo = (HttpServletResponse) response;

        respo.setCharacterEncoding("UTF-8");
        if(reque.getMethod().equals("POST")){
            String headerAuth = reque.getHeader("Authorization");

            if(headerAuth.equals("jphwany")){
                chain.doFilter(reque, respo);
            }
            else{
                PrintWriter writer = respo.getWriter();
                writer.println("인증 실패");
            }
        }
    }
}
/*
    HttpServletRequest
    => ServletRequest 를 상속
       Http 프로토콜의 request 정보를 서블릿에 전달하기 위한 목적
       Header 정보, Parameter, cookie, URI, URL 등의 정보를 읽어들이는 메소드를 가진 클래스
       Body 의 Stream 을 읽어들이는 메소드 가짐

    HttpServletResponse
    => ServletResponse 를 상속
       Servlet 이 HttpServletResponse 객체에 Content Type, 응답코드, 응답 메세지 등을 담아서 전송

    HttpServletRequest, HttpServletResponse 는 http 요청을 할 때 요청 정보가 해당 객체에 있기 때문에 사용

 */
