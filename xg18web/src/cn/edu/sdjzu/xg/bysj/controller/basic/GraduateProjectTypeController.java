package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/graduateProjectType.ctl")
public class GraduateProjectTypeController extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null的新课题类别","no":"05","remarks":""}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的课题类别","id":1,"no":"05","remarks":""}

    /**
     * POST, http://localhost:8080/graduateProjectType.ctl, 增加课题类别
     * 增加一个课题类别对象：将来自前端请求的JSON对象，增加到数据库表中
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String graduateProjectType_json = JSONUtil.getJSON(request);

        //将JSON字串解析为GraduateProjectType对象
        GraduateProjectType graduateProjectTypeToAdd = JSON.parseObject(graduateProjectType_json, GraduateProjectType.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。
        graduateProjectTypeToAdd.setId(4 + (int) (Math.random() * 100));

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GraduateProjectType对象
        try {
            GraduateProjectTypeService.getInstance().add(graduateProjectTypeToAdd);
            message.put("message", "增加成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE, http://localhost:8080/graduateProjectType.ctl?id=1, 删除id=1的课题类别
     * 删除一个课题类别对象：根据来自前端请求的id，删除数据库表中id的对应记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的课题类别
        try {
            GraduateProjectTypeService.getInstance().delete(id);
            message.put("message", "删除成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    /**
     * PUT, http://localhost:8080/graduateProjectType.ctl, 修改课题类别
     * <p>
     * 修改一个课题类别对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String graduateProjectType_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProjectType对象
        GraduateProjectType graduateProjectTypeToAdd = JSON.parseObject(graduateProjectType_json, GraduateProjectType.class);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改GraduateProjectType对象对应的记录
        try {
            GraduateProjectTypeService.getInstance().update(graduateProjectTypeToAdd);
            message.put("message", "修改成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * GET, http://localhost:8080/graduateProjectType.ctl?id=1, 查询id=1的课题类别
     * GET, http://localhost:8080/graduateProjectType.ctl, 查询所有的课题类别
     * 把一个或所有课题类别对象响应到前端
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有课题类别对象，否则响应id指定的课题类别对象
            if (id_str == null) {
                responseGraduateProjectTypes(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseGraduateProjectType(id, response);
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应一个课题类别对象
    private void responseGraduateProjectType(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找课题类别
        GraduateProjectType graduateProjectType = GraduateProjectTypeService.getInstance().find(id);
        String graduateProjectType_json = JSON.toJSONString(graduateProjectType);

        //响应graduateProjectType_json到前端
        response.getWriter().println(graduateProjectType_json);
    }

    //响应所有课题类别对象
    private void responseGraduateProjectTypes(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有课题类别
        Collection<GraduateProjectType> graduateProjectTypes = GraduateProjectTypeService.getInstance().findAll();
        String graduateProjectTypes_json = JSON.toJSONString(graduateProjectTypes, SerializerFeature.DisableCircularReferenceDetect);

        //响应graduateProjectTypes_json到前端
        response.getWriter().println(graduateProjectTypes_json);
    }
}
