package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(filterName = "Filter 20",
//        urlPatterns = "/*"/*通配符（*）表示对所有的web资源有效*/)
public class Filter20 implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Filter 20 begins");
        chain.doFilter(req, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        System.out.println("Filter 20 ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
