package com.example.demo.Util;


import com.example.demo.DTO.UsuarioDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpResponse;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterconfig) throws ServletException{}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();
        String token = req.getHeader("Authorization");

        if(path.equals("/user/new") || path.equals("/user/login")){
            chain.doFilter(request, response);
            return;
        }

        if(token == null || JwtUtil.validarToken(token.substring(7)) == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"Error\" : \"Token ausente ou inválido\"}");
            return;
        }
        chain.doFilter(request, response);
    }
}
