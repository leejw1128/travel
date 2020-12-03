package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService=new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    /**
     * @Author leejunwei
     * @Description //按页查询
     * @Date 21:15 2020/11/27/027
     * @Param [request, response]
     * @return void
     **/
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr=request.getParameter("cid");
        String currentPageStr=request.getParameter("currentPage");
        String pageSizeStr=request.getParameter("pageSize");
        String rname=request.getParameter("rname");
        rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
        int cid=0;
        int currentPage=0;
        int pageSize=0;
        if(cidStr!=null&&cidStr.length()!=0){
            cid=Integer.parseInt(cidStr);
        }else {
            cid=1;
        }
        if(currentPageStr!=null&&currentPageStr.length()!=0){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }
        if(pageSizeStr!=null&&pageSizeStr.length()!=0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;
        }
        PageBean<Route>pageBean=routeService.findByPage(cid,currentPage,pageSize,rname);
        writeValue(pageBean,response);
    }
    public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid=request.getParameter("rid");
        Route route=routeService.findRoute(rid);
        writeValue(route,response);
    }
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid=request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid=request.getParameter("rid");
        User user= (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            return;
        }else {
            uid=user.getUid();
        }
        favoriteService.add(rid,uid);
    }
    public void findPopular(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String cidStr=request.getParameter("cid");
        String pageSizeStr=request.getParameter("pageSize");
        List<Route> routeList = routeService.findPopular(cidStr, pageSizeStr);
        writeValue(routeList,response);
    }
    public void findNewest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String cidStr=request.getParameter("cid");
        String pageSizeStr=request.getParameter("pageSize");
        List<Route>routeList=routeService.findNewest(cidStr, pageSizeStr);
        writeValue(routeList,response);
    }
    public void findThemeTour(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String cidStr=request.getParameter("cid");
        String pageSizeStr=request.getParameter("pageSize");
        List<Route>routeList=routeService.findThemeTour(cidStr, pageSizeStr);
        writeValue(routeList,response);
    }
}
