package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "Filter 20",
//        urlPatterns = {"/*"}/* 仅所有资源进行过滤*/)
public class SensitiveFilter implements Filter {
    public void destroy() {    }

    /**
     * 过滤敏感词
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        System.out.println("Filter 0 - encoding begins");

        HttpServletRequest request = (HttpServletRequest)req;
        String requestMethod = request.getMethod();
        if("POST".equalsIgnoreCase(requestMethod)) {
            HttpServletResponse response = (HttpServletResponse) resp;
            CleanedRequest cleanedRequest = new CleanedRequest(request);
            System.out.println(requestMethod);
            chain.doFilter(cleanedRequest, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        }else {
            chain.doFilter(req, resp);//执行其它过滤器，如过滤器已经执行完毕，则执行原请求
        }
        System.out.println("Filter 0 - encoding ends");
    }
    public void init(FilterConfig config) throws ServletException {    }
}
