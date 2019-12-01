package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

//@WebFilter(filterName = "Filter 10",
//        urlPatterns = "/*"/*通配符（*）表示对所有的web资源有效*/)
public class Filter10 implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        String path = request.getRequestURI();
        System.out.println("请求的资源为：" + path);
        System.out.println("Filter 10 begins");
        chain.doFilter(req, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        System.out.println("Filter 10 ends");
    }
    public void init(FilterConfig config) throws ServletException {

    }
}
