package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServcie;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
    /*
     * @Author leejunwei
     * @Description //User功能的汇总,简化代码
     * @Param
     * @return
     **/
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
   /*
    * @Author leejunwei
    * @Description //实现注册功能
    * @Date 21:08 2020/11/24/024
    * @Param [request, response]
    * @return void
    **/
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String check=request.getParameter("check");
        HttpSession session=request.getSession();
        String checkcode_server= (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info=new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误啦！");
            ObjectMapper mapper=new ObjectMapper();
            String json= mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        Map<String ,String[]> map=request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserServcie servcie=new UserServiceImpl();
        boolean flag = servcie.regist(user);
        ResultInfo info=new ResultInfo();
        if (flag){
            info.setFlag(true);
            //成功
        }else{
            //失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        ObjectMapper mapper=new ObjectMapper();
        String json=mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    /*
     * @Author leejunwei
     * @Description //实现登录功能
     * @Date 21:08 2020/11/24/024
     * @Param [request, response]
     * @return void
     **/
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserServcie servcie=new UserServiceImpl();
        User u=servcie.login(user);
        ResultInfo info=new ResultInfo();
        if (u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }
        if (u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("你尚未激活，请激活");
        }
        if (u!=null&&"Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    /*
     * @Author leejunwei
     * @Description //实现查找单个用户功能
     * @Date 21:10 2020/11/24/024
     * @Param [request, response]
     * @return void
     **/
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Object user=request.getSession().getAttribute("user");
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
    /*
     * @Author leejunwei
     * @Description //实现用户退出功能
     * @Date 21:11 2020/11/24/024
     * @Param [request, response]
     * @return void
     **/
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    /*
     * @Author leejunwei
     * @Description //实现用户激活功能
     * @Date 21:13 2020/11/24/024
     * @Param
     * @return
     **/
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String code=request.getParameter("code");
        if (code!=null){
            UserServcie servcie=new UserServiceImpl();
            boolean flag=servcie.active(code);
            String msg=null;
            if (flag){
                String uri=request.getContextPath();
                System.out.println(uri);
                msg="激活成功，请<a href='../login.html'>登录</a>";
            }else {
                msg="激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
