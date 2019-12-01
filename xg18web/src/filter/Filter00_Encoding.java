package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "Filter 0",
//        urlPatterns = {"/*"}/* 仅所有资源进行过滤*/)
public class Filter00_Encoding implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Filter 0 - encoding begins");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获得path
        String path = request.getRequestURI();
        //如果没有字串"/login"，则考虑设置字符编码
        if (!path.contains("/myapp")) {
            if (!path.contains("/login")) {
                //所有的资源都要设置响应字符集
                response.setContentType("text/html;charset=UTF-8");
                //获得请求方法
                String method = request.getMethod();
                //如果是方法是POST或PUT
                if ("POST-PUT".contains(method)) {
                    //设置请求字符集
                    request.setCharacterEncoding("UTF-8");
                }
            }
        }
        chain.doFilter(req, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        System.out.println("Filter 0 - encoding ends");
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
