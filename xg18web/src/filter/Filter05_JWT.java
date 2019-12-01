package filter;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.JwtUtil;
import io.jsonwebtoken.Claims;

@WebFilter(filterName = "Filter05_JWT", urlPatterns = { "/*" })
public class Filter05_JWT implements Filter {


    @Override
    public void destroy() {
        /* 销毁时调用 */
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        /* 过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理 */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        PrintWriter out = response.getWriter();

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        // jws设置在Header的Authorization里面
        String tokenStr = request.getHeader("Authorization");
        System.out.println("tokenStr" + tokenStr);
        // 用户未登录
        if (tokenStr == null || tokenStr.equals("")) {
//            RtnValue = 1004;
//            msg = "未登录";
//            lr = new Result(RtnValue, flag, msg);
//            out.print(JSON.toJSONString(lr));
            message.put("message", "未登录");
            out.flush();
            out.close();
            return;
        }
        // 解析jws
        JSONObject validateResult = JwtUtil.validateJWT(tokenStr);
        if (validateResult.getBooleanValue("Success")) { // 解析成功
            Claims claims = (Claims) validateResult.get("Claims");
            String username = claims.getId();
            // 与数据库里的比较
            // true
            chain.doFilter(request, response);
            // false
            /*
             * RtnValue = 1004;
             * msg = "登录失效";
             * lr = new Result(RtnValue, flag, msg);
             * out.print(JSON.toJSONString(lr));
             * out.flush();
             * out.close();
             */
        } else { // 解析失败
            int errCode = validateResult.getIntValue("ErrCode");
            String errString = null;
            switch (errCode) {
                case 1005:
                    errString = "签名过期";
                    break;
                case 1004:
                    errString = "未登录";
                    break;
                default:
                    break;
            }
            message.put("message", errString);
            out.print(message);
            out.flush();
            out.close();
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}

