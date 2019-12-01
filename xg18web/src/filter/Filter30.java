package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(filterName = "Filter 30",
//        urlPatterns = {"/basic/*","/login/*"}/* 仅为指定的目录下的资源进行过滤*/)
public class Filter30 implements Filter {
    public void destroy() {    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Filter 30 - basic and login begins");
        chain.doFilter(req, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        System.out.println("Filter 30 - basic and login ends");
    }
    public void init(FilterConfig config) throws ServletException {    }
}
