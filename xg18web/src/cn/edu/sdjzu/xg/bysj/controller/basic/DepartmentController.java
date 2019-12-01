package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
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
@WebServlet("/department.ctl")
public class DepartmentController extends HttpServlet {
//    private static Logger log = LoggerFactory.g
    Logger  logger = Logger.getLogger("com.foo");
    //请使用以下JSON测试增加功能(id为空)

    //{"description":"id为null新系","no":"0201","remarks":"","department":{"description":"管理工程","id":2,"no":"02","remarks":"最好的系"}}

    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的系","id":1,"no":"0201","remarks":"","department":{"description":"管理工程","id":2,"no":"02","remarks":"最好的系"}}

    /**
     * POST, http://localhost:8080/department.ctl, 增加系
     * 增加一个系对象：将来自前端请求的JSON对象，增加到数据库表中
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Request encoding="+request.getCharacterEncoding());
        System.out.println("Response encoding="+response.getCharacterEncoding());
        //根据request对象，获得代表参数的JSON字串
        String department_json = JSONUtil.getJSON(request);

        //将JSON字串解析为Department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。
        departmentToAdd.setId(4 + (int) (Math.random() * 100));


        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Department对象
        try {
            DepartmentService.getInstance().add(departmentToAdd);
            message.put("message", "增加成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            logger.error(e.getStackTrace());
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE, http://localhost:8080/department.ctl?id=1, 删除id=1的系
     * 删除一个系对象：根据来自前端请求的id，删除数据库表中id的对应记录
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

//        //设置响应字符编码为UTF-8
        System.out.println("Request encoding="+request.getCharacterEncoding());
        System.out.println("Response encoding="+response.getCharacterEncoding());
//        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的系
        try {
            boolean deleted = DepartmentService.getInstance().delete(id);
            if(deleted) {
                message.put("message", "删除成功");
            }else{
                message.put("message", "无法删除或已经被其他用户删除");
            }
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
     * PUT, http://localhost:8080/department.ctl, 修改系
     * <p>
     * 修改一个系对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录     *
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Request encoding="+request.getCharacterEncoding());
        System.out.println("Response encoding="+response.getCharacterEncoding());
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Department对象对应的记录
        try {
            DepartmentService.getInstance().update(departmentToAdd);
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
     * GET, http://localhost:8080/department.ctl?id=1, 查询id=1的系
     * GET, http://localhost:8080/department.ctl, 查询所有的系
     * 把一个或所有系对象响应到前端
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Request encoding="+request.getCharacterEncoding());
        System.out.println("Response encoding="+response.getCharacterEncoding());
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        String paraType = request.getParameter("paraType");

        //读取参数id
        String id_str = request.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //paraType为“School”,则是查询指定学院下辖的所有系
            if("school".equalsIgnoreCase(paraType)){
                Integer schoolId = Integer.parseInt(id_str);
                responseDepartmentsBySchool(schoolId, response);
            } else if (id_str == null) {
                //如果id = null, 表示响应所有系对象，否则响应id指定的系对象
                responseDepartments(response);
            } else {
                Integer id = Integer.parseInt(id_str);
                responseDepartment(id, response);
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

    //响应一个系对象
    private void responseDepartment(Integer id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找系
        Department department = DepartmentService.getInstance().find(id);
        String department_json = JSON.toJSONString(department);

        //响应department_json到前端
        response.getWriter().println(department_json);
    }

    //响应所有系对象
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有系
        Collection<Department> departments = DepartmentService.getInstance().findAll();
        String departments_json = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);

        //响应departments_json到前端
        response.getWriter().println(departments_json);
    }
    //根据学院id，查询所有的系
    private void responseDepartmentsBySchool(Integer schoolId, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有系
        Collection<Department> departments = DepartmentService.getInstance().findAllBySchool(schoolId);
        String departments_json = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);

        //响应departments_json到前端
        response.getWriter().println(departments_json);

    }
}
