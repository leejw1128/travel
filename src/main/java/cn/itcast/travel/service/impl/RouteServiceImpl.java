package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route>pageBean=new PageBean<Route>();
        //在这里对数据进行组装，按照pageBean类的要求
        //1.获取所有数据,总记录数
        int totalCount=routeDao.findTotal(cid,rname);

        int start=(currentPage-1) * pageSize;
        List<Route>routeList=routeDao.findAll(cid,start,pageSize,rname);

        int totalPage=0;
        totalPage=totalCount % pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;
        pageBean.setPageSize(pageSize);
        pageBean.setList(routeList);
        pageBean.setTotalPage(totalPage);
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }

    @Override
    public Route findRoute(String rid) {
        Route route=routeDao.findRoute(Integer.parseInt(rid));
        Seller seller=sellerDao.findById(route.getSid());
        List<RouteImg>routeImgList = routeImgDao.findById(route.getRid());
        route.setRouteImgList(routeImgList);
        route.setSeller(seller);
        int count=favoriteDao.findCountByRid(route.getRid());
        return route;
    }

    @Override
    public List<Route> findPopular(String cidStr,String pageSizeStr) {
        List<Route> routeList = routeDao.findPopular(Integer.parseInt(cidStr), Integer.parseInt(pageSizeStr));
        return routeList;
    }

    @Override
    public List<Route> findNewest(String cidStr, String pageSizeStr) {
        List<Route> routeList = routeDao.findNewest(Integer.parseInt(cidStr), Integer.parseInt(pageSizeStr));
        return routeList;
    }

    @Override
    public List<Route> findThemeTour(String cidStr, String pageSizeStr) {
        List<Route> routeList = routeDao.findThemeTour(Integer.parseInt(cidStr), Integer.parseInt(pageSizeStr));
        return routeList;
    }

}
