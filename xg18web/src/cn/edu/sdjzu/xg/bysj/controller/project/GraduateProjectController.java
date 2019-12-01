package cn.edu.sdjzu.xg.bysj.controller.project;

import cn.edu.sdjzu.xg.bysj.domain.GraduateProject;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectService;
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
@WebServlet("/graduateProject.ctl")
public class GraduateProjectController extends HttpServlet {
    //请使用以下JSON测试增加功能
    //{"degree":{"description":"博士","id":1,"no":"01","remarks":""},"department":{"description":"信息管理","id":2,"no":"0202","remarks":"","graduateProject":{"description":"管理工程","id":2,"no":"02","remarks":"最好的教师"}},"id":7,"name":"id=7 的新的新老师","title":{"description":"副教授","id":2,"no":"02","remarks":""}}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的老师
    //{"degree":{"description":"博士","id":1,"no":"01","remarks":""},"department":{"description":"信息管理","id":2,"no":"0202","remarks":"","graduateProject":{"description":"管理工程","id":2,"no":"02","remarks":"最好的教师"}},"id":1,"name":"修改id=1的老师","title":{"description":"副教授","id":2,"no":"02","remarks":""}}


    /**
     * POST, http://localhost:8080/graduateProject.ctl, 增加教师
     * 增加一个教师对象：将来自前端请求的JSON对象，增加到数据库表中
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
        String graduateProject_json = JSONUtil.getJSON(request);

        //将JSON字串解析为GraduateProject对象
        GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。
        graduateProjectToAdd.setId(4 + (int) (Math.random() * 100));

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GraduateProject对象
        try {
            GraduateProjectService.getInstance().add(graduateProjectToAdd);
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
     * DELETE, http://localhost:8080/graduateProject.ctl?id=1, 删除id=1的教师
     * 删除一个教师对象：根据来自前端请求的id，删除数据库表中id的对应记录
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

        //到数据库表中删除对应的教师
        try {
            GraduateProjectService.getInstance().delete(id);
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
     * PUT, http://localhost:8080/graduateProject.ctl, 修改教师
     * <p>
     * 修改一个教师对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
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
        String graduateProject_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProject对象
        GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改GraduateProject对象对应的记录
        try {
            GraduateProjectService.getInstance().update(graduateProjectToAdd);
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
     * GET, http://localhost:8080/graduateProject.ctl?id=1, 查询id=1的教师
     * GET, http://localhost:8080/graduateProject.ctl, 查询所有的教师
     * 把一个或所有教师对象响应到前端
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
            //如果id = null, 表示响应所有教师对象，否则响应id指定的教师对象
            if (id_str == null) {
                responseGraduateProjects(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseGraduateProject(id, response);
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

    //响应一个教师对象
    private void responseGraduateProject(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找教师
        GraduateProject graduateProject = GraduateProjectService.getInstance().find(id);
        String graduateProject_json = JSON.toJSONString(graduateProject);

        //响应graduateProject_json到前端
        response.getWriter().println(graduateProject_json);
    }

    //响应所有教师对象
    private void responseGraduateProjects(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有教师
        Collection<GraduateProject> graduateProjects = GraduateProjectService.getInstance().findAll();
        String graduateProjects_json = JSON.toJSONString(graduateProjects, SerializerFeature.DisableCircularReferenceDetect);

        //响应graduateProjects_json到前端
        response.getWriter().println(graduateProjects_json);
    }
}
