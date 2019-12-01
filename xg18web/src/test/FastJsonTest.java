package test;

import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.sql.SQLException;
import java.util.Collection;

public class FastJsonTest {
    public static void main(String[] args) throws SQLException {
        //获得所有学院
        Collection<School> schools = SchoolService.getInstance().findAll();
        //将集合类对象转换为json字串
        String schools_str = JSON.toJSONString(schools);
        System.out.println(schools_str);
    }
}

//    Collection<Teacher> teachers = TeacherService.getInstance().findAll();
//    String str1= JSON.toJSONString(teachers, SerializerFeature.DisableCircularReferenceDetect);
//        System.out.println(str1);
